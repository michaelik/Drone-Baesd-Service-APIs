package com.dronebasedserviceapi.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DroneDeliveryRequest {
	@NotBlank
	@NotNull
	private String serialNumber;
}
