package com.dronebasedserviceapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dronebasedserviceapi.controller.DroneController;
import com.dronebasedserviceapi.model.Drone;
import com.dronebasedserviceapi.model.Medication;
import com.dronebasedserviceapi.payload.request.DroneBatteryRequest;
import com.dronebasedserviceapi.payload.request.LoadDroneRequest;
import com.dronebasedserviceapi.payload.request.RegisterDroneRequest;
import com.dronebasedserviceapi.payload.response.AvailableDroneResponse;
import com.dronebasedserviceapi.payload.response.DroneBatteryResponse;
import com.dronebasedserviceapi.payload.response.DroneMedicationItemResponse;
import com.dronebasedserviceapi.payload.response.LoadDroneResponse;
import com.dronebasedserviceapi.payload.response.RegisterDroneResponse;
import com.dronebasedserviceapi.service.DroneServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TestDroneController {

	private MockHttpServletRequest request;
	private LocalDateTime time;

	@InjectMocks
	DroneController droneController;

	@Mock
	DroneServiceImpl droneService;

	@BeforeEach
	void init() {
		request = new MockHttpServletRequest();
		time = java.time.LocalDateTime.now();
	}

	@Test
	void testRegisterNewDrone() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		RegisterDroneResponse registerDroneResponse = new RegisterDroneResponse();
		registerDroneResponse.setResult("success");
		registerDroneResponse.setSerialNumber("IK15690241");
		registerDroneResponse.setMessage("New Drone created successfully");
		registerDroneResponse.setTimestamp(time);

		when(droneService.registerNewDrone(any(RegisterDroneRequest.class))).thenReturn(registerDroneResponse);

		RegisterDroneRequest registerDroneRequest = new RegisterDroneRequest();
		registerDroneRequest.setSerialNumber("IK15690241");
		registerDroneRequest.setModel("Lightweight");
		registerDroneRequest.setWeightLimit(430.0);
		registerDroneRequest.setBattery(new BigDecimal(0.95));
		registerDroneRequest.setState("IDLE");

		ResponseEntity<RegisterDroneResponse> responseEntity = droneController.registerDrone(registerDroneRequest);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(responseEntity.getBody()).isEqualTo(registerDroneResponse);
	}

	@Test
	public void testGetDroneBatteryLevel() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		DroneBatteryResponse droneBatteryResponse = new DroneBatteryResponse();
		droneBatteryResponse.setStatus("success");
		droneBatteryResponse.setSerialNumber("IK15690241");
		droneBatteryResponse.setBattery("98%");
		droneBatteryResponse.setTimestamp(time);

		when(droneService.getBateryLevel(any(DroneBatteryRequest.class))).thenReturn(droneBatteryResponse);

		DroneBatteryRequest droneBatteryRequest = new DroneBatteryRequest();
		droneBatteryRequest.setSerialNumber("IK15690241");
		ResponseEntity<DroneBatteryResponse> responseEntity = droneController.checkDroneBattery(droneBatteryRequest);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(responseEntity.getBody()).isEqualTo(droneBatteryResponse);
	}
	
	@Test
	void testGetAvailableDroneForLoading() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		List<Drone> list = new ArrayList<Drone>();
		Drone drone1 = new Drone("IK15690241", "Lightweight", 430.0, new BigDecimal(0.95), "IDLE");
		Drone drone2 = new Drone("IK15690242", "Heavyweight", 400.0, new BigDecimal(0.10), "IDLE");
		list.add(drone1);
		list.add(drone2);

		AvailableDroneResponse AvailableDroneResponse = new AvailableDroneResponse("success", 
				                                                                   java.time.LocalDateTime.now(),
			                                                                       list);

		when(droneService.getAvailabeDrones()).thenReturn(AvailableDroneResponse);

		ResponseEntity<AvailableDroneResponse> responseEntity = droneController.getAvailableDroneForLoading();
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody().getStatus()).isEqualTo("success");
		assertThat(responseEntity.getBody().getDrones().size()).isEqualTo(2);
	}
	
	@Test
	public void testLoadDroneWithMedication() {
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		LoadDroneResponse droneResponse = new LoadDroneResponse();
		droneResponse.setResult("sucess");
		droneResponse.setSerialNumber("Q23RT5676697");
		droneResponse.setMessage("Drone Loaded successfully");
		droneResponse.setTimestamp(java.time.LocalDateTime.now());

		when(droneService.loadDroneWithMedication(any(LoadDroneRequest.class))).thenReturn(droneResponse);

		LoadDroneRequest loadDroneRequest = new LoadDroneRequest();
		loadDroneRequest.setSerialNumber("IK15690241");
		loadDroneRequest.setSource("India");
		loadDroneRequest.setDestination("USA");
		loadDroneRequest.setCode("WE121212");
		
		ResponseEntity<LoadDroneResponse> responseEntity = droneController.loadDroneWithMedication(loadDroneRequest);
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(responseEntity.getBody()).isEqualTo(droneResponse);

	}
	
	@Test
	public void testCheckLoadedMedicationItem() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Medication medication = new Medication("WE121212", "Abilify", 100, "sade23rd");
		
		DroneMedicationItemResponse droneMedicationItemResponse = new DroneMedicationItemResponse();
		droneMedicationItemResponse.setResult("success");
		droneMedicationItemResponse.setSerialNumber("IK15690241");
		droneMedicationItemResponse.setTimestamp(java.time.LocalDateTime.now());
		droneMedicationItemResponse.setMedication(medication);

		String serialNumber = "IK15690241";
		when(droneService.getLoadedMedicationForADrone(serialNumber)).thenReturn(droneMedicationItemResponse);

		ResponseEntity<DroneMedicationItemResponse> responseEntity = droneController.checkLoadedMedicationItem(serialNumber);
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(droneMedicationItemResponse);
	}
}
