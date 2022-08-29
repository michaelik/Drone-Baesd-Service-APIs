package com.dronebasedserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dronebasedserviceapi.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, String>{
	@Query(value = "SELECT * from medication e where e.code =:code ", nativeQuery = true)
	Medication findByCode(@Param("code") String code);
}
