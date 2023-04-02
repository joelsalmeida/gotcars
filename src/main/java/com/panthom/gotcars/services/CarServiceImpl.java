package com.panthom.gotcars.services;

import com.panthom.gotcars.mappers.CarMapper;
import com.panthom.gotcars.model.CarDTO;
import com.panthom.gotcars.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public CarDTO save(CarDTO car) {
        return carMapper.entityToDTO(carRepository.save(carMapper.dtoToEntity(car)));
    }

    @Override
    public List<CarDTO> getAll() {
        return carRepository.findAll().stream().map(carMapper::entityToDTO).toList();
    }
}
