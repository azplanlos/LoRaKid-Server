package de.goforfun.lorakid.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class StringMapping {

	@JsonProperty("kurz")
	@NonNull
	private String kurz;

	@JsonProperty("auswahl")
	private Map<String, Integer> auswahl;

	@JsonProperty("code")
	private int code;

	@JsonProperty("anzeige")
	@NonNull
	private List<DisplayClientType> anzeige;

	@JsonProperty("antwort")
	private List<String> antwort;

	@JsonProperty("text")
	@NonNull
	private String text;
}