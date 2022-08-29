package com.dronebasedserviceapi.payload.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DroneBatteryResponse {
	private String status;
	private String serialNumber;
	private String battery;
	private LocalDateTime timestamp;
}
