package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.model.CarModel;
import com.team.fithniti.demo.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v2")
public class CarModelController {

    @Autowired
    CarModelService carModelService;

    @GetMapping("/car-models")
    public ResponseEntity<List<CarModel>> getAllModels(@RequestParam(name = "brand") String brand) {
        List<CarModel> carModels = carModelService.getAllModels(brand);
        return new ResponseEntity<>(carModels, HttpStatus.OK);
    }

    @GetMapping("/car-models/{id}")
    public CarModel getCarModel(@PathVariable("id") Long carModelId, @RequestParam(name = "brand") String brand) {
        CarModel carModel = carModelService.getCarModel(carModelId, brand);
        return carModel;
    }

    @PostMapping("/car-models")
    public ResponseEntity<String> addCarModel(@RequestBody CarModel newCarModel, @RequestParam(name = "brand") String brand) {
        carModelService.create(newCarModel, brand);
        return new ResponseEntity<>("New car model added", HttpStatus.OK);
    }

    @PutMapping("/car-models/{id}")
    public ResponseEntity<String> updateCarModel(@RequestBody CarModel updatedCarModel,
                                                 @RequestParam(name = "brand") String brand,
                                                 @PathVariable("id") Long id) {
        carModelService.update(brand, id,updatedCarModel);
        return new ResponseEntity<>("Car model updated", HttpStatus.OK);
    }

    @DeleteMapping("/car-models/{id}")
    public ResponseEntity<String> deleteCarModel(@PathVariable("id") Long carModelId, @RequestParam(name = "brand") String brand) {
        carModelService.delete(carModelId, brand);
        return new ResponseEntity<>("Car model deleted", HttpStatus.OK);
    }

}
