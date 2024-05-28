package de.goforfun.lorakid;

import de.goforfun.lorakid.domain.DisplayClientType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SettingsTest {

    private final LoRaKidSettings settings_underTest = new LoRaKidSettings();

    @Test
    void testJsonConversion() throws IOException {
        byte[] bytes = settings_underTest.convertJsonToProtoBuf(this.getClass().getClassLoader().getResourceAsStream("stringMappings.json"), DisplayClientType.KID);
        assertThat(bytes).hasSizeGreaterThan(10);
        System.out.println(Arrays.toString(bytes));
        System.out.println(bytes.length);
    }
}
