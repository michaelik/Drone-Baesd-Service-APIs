package com.dronebasedserviceapi.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoadDroneRequest {
	@NotNull
	@NotBlank
	private String serialNumber;
	
	@NotNull
	@NotBlank
	private String code;
	
	@NotNull
	@NotBlank
	private String source;
	
	@NotNull
	@NotBlank
	private String destination;
}
