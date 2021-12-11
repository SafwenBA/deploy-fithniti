package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.Car;
import com.team.fithniti.demo.model.CarModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO {
    private String brand;
    private String logoURL;
    private String carModel;
    private String modelImageURL;

    public static CarDTO fromEntity(Car car, CarModel carModel){
        if (car == null)
            throw new InvalidResource(null,"Invalid car","can't map null entity");
        return CarDTO.builder()
                .logoURL(car.getLogoURL())
                .brand(car.getBrand())
                .carModel(carModel.getModel())
                .modelImageURL(carModel.getImageURL())
                .build();
    }
}
