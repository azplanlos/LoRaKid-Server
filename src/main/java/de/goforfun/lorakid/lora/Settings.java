package de.goforfun.lorakid.lora;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class Settings {
	@JsonProperty("data_rate")
	private DataRate dataRate;
	@JsonProperty
	private DateTime time;
	@JsonProperty
	private String frequency;
	@JsonProperty
	private long timestamp;
}