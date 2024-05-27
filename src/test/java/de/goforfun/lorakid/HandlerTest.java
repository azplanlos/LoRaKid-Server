package de.goforfun.lorakid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.goforfun.lorakid.domain.TestPayload;
import de.goforfun.lorakid.lora.LoRaUplinkMessage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HandlerTest {

    @Test
    public void testJsonParse() throws IOException {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        LoRaUplinkMessage<TestPayload> message = mapper.readValue(this.getClass().getClassLoader().getResource("payload.json"), new TypeReference<LoRaUplinkMessage<TestPayload>>() {});
        assertThat(message.getUplinkMessage().getDecodedPayload().toString()).contains("Andi");
    }
}
