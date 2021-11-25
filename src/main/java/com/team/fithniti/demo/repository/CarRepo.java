package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepo extends JpaRepository<Car, Long> {

    @Query("select id from Car c where c.brand = ?1")
    Long getCarIdByBrand(String brand);

    //This method is used to get a car by its brand
    Car getCarByBrand(String brand);

    //This method is used to check if the car brand exists already in the database
    Boolean existsCarByBrand(String brand);
}
