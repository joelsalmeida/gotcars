package com.panthom.gotcars.mappers;

import com.panthom.gotcars.entities.CarEntity;
import com.panthom.gotcars.model.CarDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {
    CarEntity dtoToEntity(CarDTO carDTO);

    CarDTO entityToDTO(CarEntity carEntity);
}
