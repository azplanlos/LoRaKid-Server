package de.goforfun.lorakid.lora;

import lombok.Data;

@Data
public class Location{
	private int altitude;
	private double latitude;
	private String source;
	private double longitude;
}