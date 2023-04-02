package com.panthom.gotcars.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.panthom.gotcars.model.CarDTO;
import com.panthom.gotcars.services.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CarService carService;

    CarDTO mustangGT500 = CarDTO.builder().id(UUID.randomUUID()).brand("Ford").model("Shelby GT500").releaseYear((short) 2023).build();
    CarDTO phantomSeriesII = CarDTO.builder().id(UUID.randomUUID()).brand("Rolls-Royce").model("Phantom Series II").releaseYear((short) 2023).build();

    @Test
    void testPost() throws Exception {
        String carToSaveAsString = objectMapper.writeValueAsString(mustangGT500);

        given(carService.save(any(CarDTO.class))).willReturn(mustangGT500);

        mockMvc.perform(post(CarController.CAR_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carToSaveAsString))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(content().json(carToSaveAsString));
    }

    @Test
    void testGetAll() throws Exception {
        ArrayList<CarDTO> arrayList = new ArrayList<>();

        given(carService.getAll()).willReturn(arrayList);

        mockMvc.perform(get(CarController.CAR_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(content().json(objectMapper.writeValueAsString(arrayList)));

        arrayList.add(mustangGT500);
        arrayList.add(phantomSeriesII);

        given(carService.getAll()).willReturn(arrayList);

        mockMvc.perform(get(CarController.CAR_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(content().json(objectMapper.writeValueAsString(arrayList)));
    }
}