package de.goforfun.lorakid.lora;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class LoRaUplinkMessage<T> {
	@JsonProperty("correlation_ids")
	private List<String> correlationIds;
	@JsonProperty("end_device_ids")
	private EndDeviceIds endDeviceIds;
	@JsonProperty("received_at")
	private DateTime receivedAt;
	@JsonProperty("uplink_message")
	private UplinkMessage<T> uplinkMessage;
}