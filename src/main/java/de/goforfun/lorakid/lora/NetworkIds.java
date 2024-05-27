package de.goforfun.lorakid.lora;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NetworkIds {
	@JsonProperty("tenant_id")
	private String tenantId;
	@JsonProperty("cluster_id")
	private String clusterId;
	@JsonProperty("ns_id")
	private String nsId;
	@JsonProperty("net_id")
	private String netId;
	@JsonProperty("cluster_address")
	private String clusterAddress;
}