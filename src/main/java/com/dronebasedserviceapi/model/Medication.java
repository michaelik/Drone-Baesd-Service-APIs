package com.dronebasedserviceapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "medication")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Medication {
	@Id
	@Column(name = "code", columnDefinition = "VARCHAR(16) NOT NULL")
	private String code;

	@Column(name = "name", columnDefinition = "VARCHAR(30) NOT NULL")
	private String name;

	@Column(name = "weight", columnDefinition = "VARCHAR(10) NOT NULL")
	private double weight;

	@Column(name = "medication_image")
	private String image;

	public Medication (String code, String name, double weight, String image) {
		super();
		this.code = code;
		this.name = name;
		this.weight = weight;
		this.image = image;
	}

	public Medication() {
	}
}
