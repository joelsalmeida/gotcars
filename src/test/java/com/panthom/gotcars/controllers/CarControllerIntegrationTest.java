package com.panthom.gotcars.controllers;

import com.panthom.gotcars.model.CarDTO;
import com.panthom.gotcars.repositories.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;


@SpringBootTest
public class CarControllerIntegrationTest {
    @Autowired
    CarController carController;
    @Autowired
    CarRepository carRepository;

    CarDTO mustangGT500 = CarDTO.builder().id(UUID.randomUUID()).brand("Ford").model("Shelby GT500").releaseYear((short) 2023).build();

    @Test
    @Transactional
    @Rollback
    void saveNewCarIntegrationTest() {
        assertThat(carRepository.count()).isEqualTo(0);

        ResponseEntity responseEntity = carController.post(mustangGT500);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        assertThat(carRepository.count()).isEqualTo(1);
    }

}
