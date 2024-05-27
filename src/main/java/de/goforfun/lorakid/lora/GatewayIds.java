package de.goforfun.lorakid.lora;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GatewayIds{
	@JsonProperty
	private String eui;
	@JsonProperty("gateway_id")
	private String gatewayId;
}