package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.Driver;
import com.team.fithniti.demo.model.Ride;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverDto {
    private Long id;
    private double rating;
    private Integer ridesNumber;
    // coming Rides etc


    public static DriverDto fromEntity(Driver driver){
        if (driver == null)
            throw new InvalidResource(null,"INTERNAL ERROR","Can't map null entity");
        return DriverDto.builder()
                .id(driver.getId())
                .rating(driver.getRating())
                .ridesNumber(driver.getRidesNumber())
                //* to complete
                .build();
    }
}
