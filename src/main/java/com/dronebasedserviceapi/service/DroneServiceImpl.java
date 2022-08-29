package com.dronebasedserviceapi.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dronebasedserviceapi.model.Drone;
import com.dronebasedserviceapi.model.LoadMedication;
import com.dronebasedserviceapi.model.Medication;
import com.dronebasedserviceapi.model.MedicationDelivery;
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
import com.dronebasedserviceapi.repository.DroneRepository;
import com.dronebasedserviceapi.repository.LoadDroneRepository;
import com.dronebasedserviceapi.repository.MedicationDeliveryRepository;
import com.dronebasedserviceapi.repository.MedicationRepository;

@Service
public class DroneServiceImpl implements DroneService {

	@Autowired
	private DroneRepository droneRepository;
	@Autowired
	private MedicationRepository medicationRepository;
	@Autowired
	private LoadDroneRepository loadDroneRepository;
	@Autowired
	private MedicationDeliveryRepository medicationDeliveryRepository;
	
	@Override
	public RegisterDroneResponse registerNewDrone(RegisterDroneRequest droneRequest) 
	{
		Drone addNewDrone = new Drone();
		addNewDrone.setSerialNumber(droneRequest.getSerialNumber());
		addNewDrone.setModel(droneRequest.getModel());
		addNewDrone.setWeightLimit(droneRequest.getWeightLimit());
		addNewDrone.setBattery(droneRequest.getBattery());
		addNewDrone.setState(droneRequest.getState());
		droneRepository.save(addNewDrone);

		RegisterDroneResponse registerDroneResponse = new RegisterDroneResponse();
		registerDroneResponse.setResult("success");
		registerDroneResponse.setSerialNumber(addNewDrone.getSerialNumber());
		registerDroneResponse.setMessage("New Drone created successfully");
		registerDroneResponse.setTimestamp(java.time.LocalDateTime.now());

		return registerDroneResponse;
	}

	@Override
	public DroneBatteryResponse getBateryLevel(DroneBatteryRequest droneRequest) 
	{
		Drone newdrone = new Drone();
		newdrone.setSerialNumber(droneRequest.getSerialNumber());
		Drone droneBattery = droneRepository.findBySerialNumber(newdrone.getSerialNumber());
		if (droneBattery == null) {
			throw new RuntimeException("Drone battery level details not found");
		}
		
		DecimalFormat decFormat = new DecimalFormat("#%");
		DroneBatteryResponse batteryDetailsResponse = new DroneBatteryResponse();
		batteryDetailsResponse.setStatus("success");
		batteryDetailsResponse.setSerialNumber(droneBattery.getSerialNumber());
		batteryDetailsResponse.setBattery(decFormat.format(droneBattery.getBattery()));
		batteryDetailsResponse.setTimestamp(java.time.LocalDateTime.now());

		return batteryDetailsResponse;
	}

	@Override
	public AvailableDroneResponse getAvailabeDrones()
	{
		String state = "IDLE";
		List<Drone> drones = droneRepository.findAllByState(state);
		return new AvailableDroneResponse("status", java.time.LocalDateTime.now(), drones);
	}

	@Override
	public LoadDroneResponse loadDroneWithMedication(LoadDroneRequest loadRequest) 
	{
		Medication medication1 = new Medication("WE121212","Abilify",100,"maze12Rd");
		Medication medication2 = new Medication("WE131313","Baqsimi",200,"maze13Rd");
		Medication medication3 = new Medication("WE141414","Casodex",300,"maze14Rd");
		Medication medication4 = new Medication("WE151515","Delzicol",400,"maze15Rd");
		Medication medication5  = new Medication("WE161616","Elavil",500,"maze16Rd");
		medicationRepository.save(medication1);
		medicationRepository.save(medication2);
		medicationRepository.save(medication3);
		medicationRepository.save(medication4);
		medicationRepository.save(medication5);
		
		droneRepository.setUpdateState("LOADING", loadRequest.getSerialNumber());
		Drone drone = droneRepository.findBySerialNumber(loadRequest.getSerialNumber());
		Medication medication = medicationRepository.findByCode(loadRequest.getCode());
		LoadMedication checkLoad = loadDroneRepository.findByCode(loadRequest.getCode());
		
		if(checkLoad != null) {
			throw new RuntimeException("Medication code has aready been loaded, try another one");
		}

		if (drone == null) {
			throw new RuntimeException("Drone specified does not exist");
		}

		if (medication == null) {
			throw new RuntimeException("Medication specified does not exist");
		}
		
		if (drone.getWeightLimit() < medication.getWeight()) { 
			throw new RuntimeException("The Drone cannot load more than the weight limit"); 
		}
		 
		if( drone.getBattery().compareTo(new BigDecimal(0.25)) < 0){
			throw new RuntimeException("The Drone cannot be loaded, battery below 25%");
		}
		LoadMedication loadMedication = new LoadMedication();
		loadMedication.setDrone(drone);
		loadMedication.setMedication(medication);
		loadMedication.setSource(loadRequest.getSource());
		loadMedication.setDestination(loadRequest.getDestination());
		loadMedication.setCreatedon(java.time.LocalDateTime.now());
		loadDroneRepository.save(loadMedication);
		droneRepository.setUpdateState("LOADED", loadRequest.getSerialNumber());

		LoadDroneResponse loadDroneResponse = new LoadDroneResponse();
		loadDroneResponse.setResult("success");
		loadDroneResponse.setSerialNumber(loadRequest.getSerialNumber());
		loadDroneResponse.setMessage("Drone Loaded successfully");
		loadDroneResponse.setTimestamp(java.time.LocalDateTime.now());

		return loadDroneResponse;
	}

	@Override
	public DroneMedicationItemResponse getLoadedMedicationForADrone(String serialNumber) {
		LoadMedication loadMedication = loadDroneRepository.findByDrone(serialNumber);
		if(loadMedication==null) {
			throw new RuntimeException("No load Medication details for the specified drone");
		}
		
		DroneMedicationItemResponse droneLoad = new DroneMedicationItemResponse();
		droneLoad.setResult("success");
		droneLoad.setSerialNumber(loadMedication.getDrone().getSerialNumber());
		droneLoad.setTimestamp(java.time.LocalDateTime.now());
        droneLoad.setMedication(loadMedication.getMedication());

		return droneLoad;
	}

	@Override
	public DroneDeliveryResponse getDroneDeliverStatus(DroneDeliveryRequest deliverRequest) {
		droneRepository.setUpdateState("DELIVERING", deliverRequest.getSerialNumber());
		LoadMedication loadMedication = loadDroneRepository.findByDrone(deliverRequest.getSerialNumber());
		
		if(loadMedication == null) {
			throw new RuntimeException("Drone specifies is not loaded or does not exist");
		}
		
		MedicationDelivery newdelivery = new MedicationDelivery();
		newdelivery.setLoadMedication(loadMedication);
		newdelivery.setDeliveryTime(java.time.LocalDateTime.now());
		medicationDeliveryRepository.save(newdelivery);
		
		droneRepository.setUpdateState("DELIVERED", deliverRequest.getSerialNumber());

		DroneDeliveryResponse deliverDroneResponse = new DroneDeliveryResponse();
		deliverDroneResponse.setResult("success");
		deliverDroneResponse.setSerialNumber(deliverRequest.getSerialNumber());
		deliverDroneResponse.setMessage("Delivered successfully");
		deliverDroneResponse.setTimestamp(newdelivery.getDeliveryTime());

		return deliverDroneResponse;
	}

}
