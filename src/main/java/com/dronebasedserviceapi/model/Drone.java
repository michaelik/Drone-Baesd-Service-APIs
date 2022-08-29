package com.dronebasedserviceapi.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_drone")
public class Drone {
	@Id
	@Column(name = "serial_no", columnDefinition = "VARCHAR(16) NOT NULL")
	private String serialNumber;

	@Column(name = "model", columnDefinition = "VARCHAR(50) NOT NULL") 
	private String model;
	
	@Column(name = "weight_limit", columnDefinition = "VARCHAR(10) NOT NULL")
	private double weightLimit;

	@Column(name = "battery",precision = 3, scale = 2)
	private BigDecimal battery;

	@Column(name = "drone_state", columnDefinition = "VARCHAR(20) NOT NULL") 
	private String state;

	public Drone(String serialNumber, String model, double weightLimit, BigDecimal battery, String state) {
		super();
		this.serialNumber = serialNumber;
		this.model = model;
		this.weightLimit = weightLimit;
		this.battery = battery;
		this.state = state;
	}

	public Drone() {
	}
}
