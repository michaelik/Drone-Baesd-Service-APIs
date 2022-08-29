package com.dronebasedserviceapi.payload.response;

import java.time.LocalDateTime;
import java.util.List;

import com.dronebasedserviceapi.model.Drone;

import lombok.Data;

@Data
public class AvailableDroneResponse {
	private String status;
	private LocalDateTime timestamp;
	private List<Drone> drones;
	
	public AvailableDroneResponse(String status, LocalDateTime timestamp, List<Drone> drones) {
		super();
		this.status = status;
		this.timestamp = timestamp;
		this.drones = drones;
	}
}
