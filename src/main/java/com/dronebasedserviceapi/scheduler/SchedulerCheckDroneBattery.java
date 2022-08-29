package com.dronebasedserviceapi.scheduler;

import java.text.DecimalFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.dronebasedserviceapi.model.Drone;
import com.dronebasedserviceapi.repository.DroneRepository;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SchedulerCheckDroneBattery {
	@Autowired
	private DroneRepository droneRepository;
	
	static final Logger log = LoggerFactory.getLogger(SchedulerCheckDroneBattery.class);
	
	
    @Scheduled(fixedRate = 5000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        
    	List<Drone> arrDroneBatteryLevels = droneRepository.findAll();
    	DecimalFormat decFormat = new DecimalFormat("#%");
    	
        for(int i=0; i<arrDroneBatteryLevels.size(); i++) {
        	log.info("Serial Number  "+ arrDroneBatteryLevels.get(i).getSerialNumber()+"  Battery Level is "+ decFormat.format(arrDroneBatteryLevels.get(i).getBattery()));
        }
    }
}