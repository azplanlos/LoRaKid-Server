package de.goforfun.lorakid.lora;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UplinkMessage<T> {
	@JsonProperty("settings")
	private Settings settings;
	@JsonProperty("frm_payload")
	private String frmPayload;
	@JsonProperty("session_key_id")
	private String sessionKeyId;
	@JsonProperty("received_at")
	private String receivedAt;
	@JsonProperty("f_port")
	private int fPort;
	@JsonProperty("f_cnt")
	private int fCnt;
	@JsonProperty("decoded_payload")
	private T decodedPayload;
	@JsonProperty("confirmed")
	private boolean confirmed;
	@JsonProperty("network_ids")
	private NetworkIds networkIds;
	@JsonProperty("rx_metadata")
	private List<RxMetadataItem> rxMetadata;
	@JsonProperty("consumed_airtime")
	private String consumedAirtime;
}