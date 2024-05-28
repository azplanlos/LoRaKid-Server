package de.goforfun.lorakid;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import de.goforfun.lorakid.domain.TestPayload;
import de.goforfun.lorakid.domain.payload.MessageFromKidOuterClass;
import de.goforfun.lorakid.lora.LoRaUplinkMessage;
import de.goforfun.lorakid.pushover.PushOverClient;

import java.util.Base64;
import java.util.HashMap;

public class LoRaKidPush implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        LambdaLogger logger = context.getLogger();
        PushOverClient client = new PushOverClient(System.getenv().getOrDefault("PUSHOVER_API_URL", "https://api.pushover.net"),
                System.getenv("PUSHOVER_APP_TOKEN"));
        logger.log("EVENT TYPE: " + event.getClass().toString());
        APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
        response.setIsBase64Encoded(false);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "text/html");
        response.setHeaders(headers);
        String body = event.getBody() != null ? event.getBody() : "Empty body";
        logger.log(body);
        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            LoRaUplinkMessage<TestPayload> message = mapper.readValue(body, new TypeReference<LoRaUplinkMessage<TestPayload>>() {});
            logger.log(message.getUplinkMessage().getFrmPayload());
            MessageFromKidOuterClass.MessageFromKid msg = MessageFromKidOuterClass.MessageFromKid.parseFrom(Base64.getDecoder().decode(message.getUplinkMessage().getFrmPayload()));
            logger.log(String.format("Battery level: %s %%", msg.getBatteryLevel()));
            client.sendMessage(System.getenv("PUSHOVER_USER_TOKEN"), message.getUplinkMessage().getFrmPayload());
            response.setBody("OK");
            response.setStatusCode(200);
        } catch (JsonProcessingException | InvalidProtocolBufferException e) {
            logger.log(e.getMessage());
            response.setStatusCode(500);
        }
        return response;
    }
}
