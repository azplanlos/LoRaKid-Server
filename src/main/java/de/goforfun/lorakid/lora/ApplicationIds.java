package de.goforfun.lorakid.lora;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApplicationIds {
	@JsonProperty("application_id")
	private String applicationId;
}