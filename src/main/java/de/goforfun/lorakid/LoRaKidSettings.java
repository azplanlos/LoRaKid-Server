package de.goforfun.lorakid;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.goforfun.lorakid.domain.DisplayClientType;
import de.goforfun.lorakid.domain.StringMapping;
import de.goforfun.lorakid.domain.persist.StringMappings;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class LoRaKidSettings implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(S3Event s3event, Context context) {
        try {
            LambdaLogger logger = context.getLogger();
            S3EventNotification.S3EventNotificationRecord record = s3event.getRecords().getFirst();
            String srcBucket = record.getS3().getBucket().getName();
            String srcKey = record.getS3().getObject().getUrlDecodedKey();

            S3Client s3Client = S3Client.builder().build();
            ResponseInputStream<GetObjectResponse> inoutStream = getHeadObject(s3Client, srcBucket, srcKey);

            logger.log("Successfully retrieved " + srcBucket + "/" + srcKey + " of type " + inoutStream.response().contentType());

            ObjectMapper mapper = new ObjectMapper()
                    .findAndRegisterModules()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<StringMapping> strings = mapper.readValue(inoutStream, new TypeReference<List<StringMapping>>() {
            });

            List.of(DisplayClientType.KID, DisplayClientType.PARENT).forEach(type -> {
                byte[] buf = convertJsonToProtoBuf(strings, type);
                s3Client.putObject(
                        PutObjectRequest
                                .builder()
                                .bucket(srcBucket)
                                .key(String.format("%s.binpb", type.toString().toLowerCase())).build(),
                        RequestBody.fromBytes(buf)
                );
                logger.log("output to " + String.format("%s.binpb", type.toString().toLowerCase()));
            });

            return "Ok";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseInputStream<GetObjectResponse> getHeadObject(S3Client s3Client, String bucket, String key) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        return s3Client.getObject(objectRequest);
    }

    byte[] convertJsonToProtoBuf(List<StringMapping> mappings, DisplayClientType type) {
        return StringMappings.MappingsMessage.newBuilder()
                .addAllMappings(mappings.stream()
                        .filter(mappingItem -> mappingItem.getAnzeige().contains(type))
                        .map(mappingItem ->
                                StringMappings.StringMappingMessage.newBuilder()
                                        .setCode(mappingItem.getCode())
                                        .setKurz(mappingItem.getKurz())
                                        .setText(mappingItem.getText())
                                        .addAllAntwort(mappingItem.getAntwort())
                                        .addAllAnzeige(mappingItem.getAnzeige().stream().map(disp ->
                                                disp == DisplayClientType.KID ? StringMappings.DisplayType.KID : StringMappings.DisplayType.PARENT
                                        ).toList())
                                        .putAllAuswahl(Objects.requireNonNullElse(mappingItem.getAuswahl(), Collections.emptyMap()))
                                        .build())
                        .toList())
                .build().toByteArray();
    }

    byte[] convertJsonToProtoBuf(InputStream stream, DisplayClientType type) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<StringMapping> strings = mapper.readValue(stream, new TypeReference<List<StringMapping>>() {
        });
        return convertJsonToProtoBuf(strings, type);
    }
}
