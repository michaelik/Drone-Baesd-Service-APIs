package com.dronebasedserviceapi.payload.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DroneDeliveryResponse {
	private String result;
	private String serialNumber;
	private String message;
	private LocalDateTime timestamp;
}
