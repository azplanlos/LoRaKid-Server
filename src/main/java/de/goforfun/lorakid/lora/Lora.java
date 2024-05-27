package de.goforfun.lorakid.lora;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Lora {
	@JsonProperty
	private int bandwidth;
	@JsonProperty("coding_rate")
	private String codingRate;
	@JsonProperty("spreading_factor")
	private int spreadingFactor;
}