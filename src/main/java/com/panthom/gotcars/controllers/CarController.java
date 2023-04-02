package com.panthom.gotcars.controllers;

import com.panthom.gotcars.model.CarDTO;
import com.panthom.gotcars.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CarController {
    public static final String CAR_PATH = "/cars";

    private final CarService carService;

    @PostMapping(value = CAR_PATH)
    public ResponseEntity post(@RequestBody CarDTO carToSave) {
        CarDTO savedCar = carService.save(carToSave);
        final String savedCarId = savedCar.getId().toString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", String.format("%s/%s", CAR_PATH, savedCarId));

        return new ResponseEntity<>(savedCar, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(value = CAR_PATH)
    public ResponseEntity getAll() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", CAR_PATH);

        return new ResponseEntity<>(carService.getAll(), httpHeaders, HttpStatus.OK);
    }

}
