package com.panthom.gotcars.repositories;

import com.panthom.gotcars.entities.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<CarEntity, UUID> {
}
