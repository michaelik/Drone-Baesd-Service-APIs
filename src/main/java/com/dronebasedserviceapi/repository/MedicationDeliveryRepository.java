package com.dronebasedserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dronebasedserviceapi.model.MedicationDelivery;

public interface MedicationDeliveryRepository extends JpaRepository<MedicationDelivery, Integer> {

}
