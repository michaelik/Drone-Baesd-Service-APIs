package com.dronebasedserviceapi.payload.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RegisterDroneRequest {
	@NotBlank
	@NotNull
	private String serialNumber;

	@NotBlank
	@NotNull
	private String model;

	@NotNull
	private double weightLimit;

	@NotNull
	private BigDecimal battery;

	@NotBlank
	@NotNull
	private String state;

	public String getSerialNumber() {
		return serialNumber;
	}
}
