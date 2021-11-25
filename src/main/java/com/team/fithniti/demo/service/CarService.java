package com.team.fithniti.demo.service;

import com.team.fithniti.demo.model.Car;

import java.util.List;

public interface CarService {
    List<Car> getAll();
    Car getCarById(Long id);
    Car getCarByBrand(String brand);
    Long getCarIdByBrand(String brand);
    Boolean existsCarByBrand(String brand);
    Car create(Car car);
    void updateCar(Long id, Car car);
    void deleteCar(Long id);
}
