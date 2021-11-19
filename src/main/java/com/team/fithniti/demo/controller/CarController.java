package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.dto.request.NewUser;
import com.team.fithniti.demo.model.Car;
import com.team.fithniti.demo.service.CarService;
import com.team.fithniti.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAll();
        return new ResponseEntity<>(cars,HttpStatus.OK);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") Long id) {
        Car car = carService.getCarById(id);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("/car")
    public ResponseEntity<Long> getCarIdByBrand(@RequestParam(name = "brand") String brand) {
        Long id = carService.getCarIdByBrand(brand);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/cars")
    public ResponseEntity<String> addCar(@RequestBody Car newCar) {
        carService.create(newCar);
        return new ResponseEntity<>("New car is added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<String> updateCar(@PathVariable("id") Long id, @RequestBody Car car) {
        carService.updateCar(id, car);
        return new ResponseEntity<>("Car updated successfully",HttpStatus.OK);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>("Car deleted successfully",HttpStatus.OK);
    }
}
