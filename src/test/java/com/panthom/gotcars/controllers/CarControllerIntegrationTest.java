package com.panthom.gotcars.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.panthom.gotcars.model.CarDTO;
import com.panthom.gotcars.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;


@SpringBootTest
public class CarControllerIntegrationTest {
    @Autowired
    CarController carController;
    @Autowired
    CarRepository carRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    CarDTO mustangGT500 = CarDTO.builder().id(UUID.randomUUID()).brand("Ford").model("Shelby GT500").releaseYear((short) 2023).build();
    CarDTO mustangGT500LongName = CarDTO.builder().id(UUID.randomUUID()).brand("Ford").model("Shelby GT500 Shelby GT500 Shelby GT500").releaseYear((short) 2023).build();

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

    @Test
    void saveNewCarValidationIntegrationTest() throws Exception {
        mockMvc.perform(post(CarController.CAR_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mustangGT500LongName)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)));
    }

}
