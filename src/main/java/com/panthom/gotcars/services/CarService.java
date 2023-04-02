package com.panthom.gotcars.services;

import com.panthom.gotcars.model.CarDTO;

import java.util.List;

public interface CarService {
    CarDTO save(CarDTO car);
    List<CarDTO> getAll();
}
