package com.dronebasedserviceapi.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_medical_delivery")
public class MedicationDelivery {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "delivery_time", columnDefinition = "TIMESTAMP NOT NULL")
	private LocalDateTime deliveryTime;

	@OneToOne(targetEntity = LoadMedication.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_trackingid", referencedColumnName = "trackingid")
	private LoadMedication loadMedication;
}
