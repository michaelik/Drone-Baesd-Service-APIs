package com.dronebasedserviceapi.payload.response;

import java.time.LocalDateTime;

import com.dronebasedserviceapi.model.Medication;

import lombok.Data;

@Data
public class DroneMedicationItemResponse {
	private String result;
	private String serialNumber;
	private LocalDateTime timestamp;
	Medication medication;
}
