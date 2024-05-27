package de.goforfun.lorakid.lora;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EndDeviceIds {
	@JsonProperty("join_eui")
	private String joinEui;
	@JsonProperty("device_id")
	private String deviceId;
	@JsonProperty("application_ids")
	private ApplicationIds applicationIds;
	@JsonProperty("dev_addr")
	private String devAddr;
	@JsonProperty("dev_eui")
	private String devEui;
}