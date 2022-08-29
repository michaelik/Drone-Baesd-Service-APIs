package com.dronebasedserviceapi.service;

import org.springframework.stereotype.Component;

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

@Component
public interface DroneService {
	RegisterDroneResponse registerNewDrone(RegisterDroneRequest droneRequest);
	DroneBatteryResponse getBateryLevel(DroneBatteryRequest droneRequest) throws Exception;
	AvailableDroneResponse getAvailabeDrones();
	LoadDroneResponse loadDroneWithMedication(LoadDroneRequest loadRequest);
	DroneMedicationItemResponse getLoadedMedicationForADrone(String serialNumber);
	DroneDeliveryResponse getDroneDeliverStatus(DroneDeliveryRequest deliverRequest);
}
