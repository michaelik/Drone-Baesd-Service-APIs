package com.dronebasedserviceapi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dronebasedserviceapi.model.Drone;

@Transactional
public interface DroneRepository extends JpaRepository<Drone, String> {
	@Query(value = "SELECT e from Drone e where e.serialNumber =:id ")
	Drone findBySerialNumber (@Param("id") String id);
	
	List<Drone> findAllByState (@Param("drone_state") String state);
	
	@Modifying
	@Query(value = "update Drone d set d.state =:state where  d.serialNumber=:serialno ") 
	void setUpdateState (@Param("state") String state, @Param("serialno") String serialno);
}
