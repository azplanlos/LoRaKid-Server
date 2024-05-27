package de.goforfun.lorakid.lora;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.joda.time.DateTime;

@Data
public class RxMetadataItem {
	@JsonProperty("rssi")
	private int rssi;
	@JsonProperty("uplink_token")
	private String uplinkToken;
	@JsonProperty("channel_index")
	private int channelIndex;
	@JsonProperty("snr")
	private double snr;
	@JsonProperty("received_at")
	private DateTime receivedAt;
	@JsonProperty("channel_rssi")
	private int channelRssi;
	@JsonProperty("location")
	private Location location;
	@JsonProperty("time")
	private DateTime time;
	@JsonProperty("gateway_ids")
	private GatewayIds gatewayIds;
	@JsonProperty("timestamp")
	private long timestamp;
}