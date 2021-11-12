package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.Car;
import com.team.fithniti.demo.repository.CarRepo;
import com.team.fithniti.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepo carRepo;

    @Override
    public List<Car> getAll() {
        return carRepo.findAll();
    }

    @Override
    public Car getCarById(Long id) {
        return carRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("INVALID_CAR", "No Car Found")
        );
    }

    @Override
    public Car getCarByBrand(String brand) {
        return carRepo.getCarByBrand(brand);
    }

    @Override
    public Long getCarIdByBrand(String brand) {
        Long id = carRepo.getCarIdByBrand(brand);
        if (id == null) {
            throw new ResourceNotFound("INVALID_CAR_BRAND", "This car brand does not exist");
        } else {
            return id;
        }
    }

    @Override
    public Boolean existsCarByBrand(String brand) {
        return carRepo.existsCarByBrand(brand);
    }


    @Override
    public Car create(Car newCar) {
        if(newCar == null) {
            throw new InvalidResource(null, "INVALID_CAR", "Can't persist null entity");
        }
        if (existsCarByBrand(newCar.getBrand())) {
            throw new InvalidResource(null, "INVALID_CAR", "Such car brand exists already");
        }
        return carRepo.save(newCar);
    }

    @Override
    public void updateCar(Long id, Car updatedCar) {
        Car carData = carRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("INVALID_CAR", "No Car Found")
        );
        Car _car = carData;
        if (existsCarByBrand(updatedCar.getBrand()) && updatedCar.getLogoURL().equals(_car.getLogoURL())) {
            throw new InvalidResource(null, "INVALID_CAR", "Such car brand exists already");
        }
        _car.setBrand(updatedCar.getBrand());
        _car.setLogoURL(updatedCar.getLogoURL());
        carRepo.save(_car);
    }

    @Override
    public void deleteCar(Long id) {
        carRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("INVALID_CAR", "No Car Found")
        );
        carRepo.deleteById(id);
    }
}
