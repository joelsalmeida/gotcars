package com.panthom.gotcars.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CarDTO {
    private UUID id;
    private String brand;
    private String model;
    private Short releaseYear;
}
