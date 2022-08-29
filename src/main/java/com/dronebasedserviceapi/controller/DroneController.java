package com.dronebasedserviceapi.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dronebasedserviceapi.payload.request.DroneBatteryRequest;
import com.dronebasedserviceapi.payload.request.DroneDeliveryRequest;
import com.dronebasedserviceapi.payload.request.LoadDroneRequest;
import com.dronebasedserviceapi.payload.request.RegisterDroneRequest;
import com.dronebasedserviceapi.payload.response.AvailableDroneResponse;
import com.dronebasedserviceapi.payload.response.DroneBatteryResponse;
import com.dronebasedserviceapi.payload.response.DroneDeliveryResponse;
import com.dronebasedserviceapi.payload.response.DroneMedicationItemResponse;
import com.dronebasedserviceapi.payload.response.LoadDroneResponse;
import com.dronebasedserviceapi.payload.response.RegisterDroneResponse;
import com.dronebasedserviceapi.service.DroneServiceImpl;

@RestController
@RequestMapping(path="/api/drone")
@Validated
public class DroneController {
	
	@Autowired
	private DroneServiceImpl droneService;

	@PostMapping(path="/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<RegisterDroneResponse> registerDrone(@Valid 
															   @NotNull 
															   @RequestBody 
															   RegisterDroneRequest droneRequest)
	{
		RegisterDroneResponse registerNewDrone = droneService.registerNewDrone(droneRequest);
		return new ResponseEntity<RegisterDroneResponse>(registerNewDrone, HttpStatus.CREATED);
	}
	
	@PostMapping(path ="/battery", consumes = "application/json", produces = "application/json")
	public ResponseEntity<DroneBatteryResponse> checkDroneBattery(@NotNull 
		                                                          @RequestBody(required = true) 
		                                                          DroneBatteryRequest droneRequest) 
	{
		if (droneRequest.getSerialNumber() == null || droneRequest.getSerialNumber().isEmpty()) {
			throw new RuntimeException("SerialNumber is Required");
		}
		DroneBatteryResponse droneBatteryResponse = droneService.getBateryLevel(droneRequest);
		return new ResponseEntity<DroneBatteryResponse>(droneBatteryResponse, HttpStatus.CREATED);
	}
	
	@GetMapping(path= "/available", produces = "application/json")
	public ResponseEntity<AvailableDroneResponse> getAvailableDroneForLoading() 
	{
		AvailableDroneResponse drones = droneService.getAvailabeDrones();
		return new ResponseEntity<AvailableDroneResponse>(drones, HttpStatus.OK);
	}
	
	@PostMapping(path = "/load", consumes = "application/json", produces = "application/json")
	public ResponseEntity<LoadDroneResponse> loadDroneWithMedication(@Valid 
														             @NotNull 
														             @RequestBody 
														             LoadDroneRequest loadRequest) 
	{
		LoadDroneResponse loadDrone = droneService.loadDroneWithMedication(loadRequest);
		return new ResponseEntity<LoadDroneResponse>(loadDrone, HttpStatus.CREATED);
	}
	
	@GetMapping(path = "details/{serialNumber}", produces = "application/json")
	public ResponseEntity<DroneMedicationItemResponse> checkLoadedMedicationItem(@PathVariable("serialNumber") 
	                                                                             String serialNumber) 
	{
		DroneMedicationItemResponse droneItem = droneService.getLoadedMedicationForADrone(serialNumber);
		return new ResponseEntity<DroneMedicationItemResponse>(droneItem, HttpStatus.OK);
	}
	
	@PostMapping(path = "/deliver", consumes = "application/json", produces = "application/json")
	public ResponseEntity<DroneDeliveryResponse> droneMedicalLoadDelivery(@NotNull 
																		  @RequestBody 
																		  DroneDeliveryRequest deliverRequest) 
	{
		DroneDeliveryResponse deliveryStatus = droneService.getDroneDeliverStatus(deliverRequest);
		return new ResponseEntity<DroneDeliveryResponse>(deliveryStatus, HttpStatus.CREATED);
	}
}
