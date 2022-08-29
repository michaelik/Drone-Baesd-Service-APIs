package com.dronebasedserviceapi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dronebasedserviceapi.controller.DroneController;

@SpringBootTest
class DroneBasedServiceApplicationTests {

	@Autowired
	DroneController droneController;
	@Test
	void contextLoads() {
		Assertions.assertThat(droneController)
		          .isNotNull();
	}

}
