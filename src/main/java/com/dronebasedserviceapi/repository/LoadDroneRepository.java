package com.dronebasedserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dronebasedserviceapi.model.LoadMedication;

public interface LoadDroneRepository extends JpaRepository<LoadMedication, String> {
	@Query(value = "SELECT * from tbl_drone_load e where e.fk_code =:code", nativeQuery = true)
	LoadMedication findByCode(@Param("code") String code);
	
	@Query(value = "SELECT * from tbl_drone_load e where e.fk_serial_no =:serialno", nativeQuery = true)
	LoadMedication findByDrone(@Param("serialno") String serialNumber);
}
