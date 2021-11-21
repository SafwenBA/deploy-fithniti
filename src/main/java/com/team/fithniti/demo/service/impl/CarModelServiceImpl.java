package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceExists;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.Car;
import com.team.fithniti.demo.model.CarModel;
import com.team.fithniti.demo.repository.CarModelRepo;
import com.team.fithniti.demo.service.CarModelService;
import com.team.fithniti.demo.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarModelServiceImpl implements CarModelService {

    @Autowired
    private CarModelRepo carModelRepo;

    @Autowired
    private CarService carService;

    @Override
    public CarModel create(CarModel newCarModel, String brand) {
        if(!carService.existsCarByBrand(brand)) {
            throw new ResourceNotFound("INVALID_CAR", "There is no such car brand");
        }
        if (existsCarModelByCarBrandAndModel(brand, newCarModel.getModel())) {
            throw new ResourceExists("EXIST", "This car model name exists already");
        }
        Car car = carService.getCarByBrand(brand);
        newCarModel.setCar(car);
        car.getCarModels().add(newCarModel);
        return carModelRepo.save(newCarModel);
    }

    @Override
    public Boolean existsCarModelByCarBrandAndModel(String brand, String model) {
        if(!carService.existsCarByBrand(brand)) {
            throw new ResourceNotFound("INVALID_CAR", "There is no such car brand");
        }
        return carModelRepo.existsCarModelByCarBrandAndModel(brand, model);
    }

    @Override
    public void update(String brand, Long carModelId, CarModel updatedCarModel) {
        if(!carService.existsCarByBrand(brand)) {
            throw new ResourceNotFound("INVALID_CAR", "There is no such car brand");
        }
        CarModel _carModel = getCarModel(carModelId, brand);
        if (existsCarModelByCarBrandAndModel(brand, updatedCarModel.getModel()) && updatedCarModel.getImageURL().equals(_carModel.getImageURL())) {
            throw new ResourceExists("EXIST", "This car model name exists already");
        }

        _carModel.setModel(updatedCarModel.getModel());
        _carModel.setImageURL(updatedCarModel.getImageURL());
        carModelRepo.save(_carModel);
    }

    @Override
    public void delete(Long carModelId, String brand) {
        carModelRepo.deleteCarModelById(carModelId, brand);
    }

    @Override
    public List<CarModel> getAllModels(String brand) {
        if (!carService.existsCarByBrand(brand)) {
            throw new ResourceNotFound("INVALID_CAR", "No Car Found");
        }
        Car car = carService.getCarByBrand(brand);
        return car.getCarModels();
    }

    @Override
    public CarModel getCarModel(Long carModelId, String brand) {
        if(!carService.existsCarByBrand(brand)) {
            throw new ResourceNotFound("INVALID_CAR", "No car found for this brand");
        }
        List<CarModel> carModels = getAllModels(brand);
        if (carModels.size() == 0) {
            throw new InvalidResource(null, "INVALID_CAR", "This brand has no car models");
        }
        List<CarModel> carModelFound = carModels.stream()
                .filter(carModel ->  carModel.getId() == carModelId)
                .collect(Collectors.toList());
        if (carModelFound.size() == 0) {
            throw new ResourceNotFound("INVALID_CAR_MODEL", "No Car Model Found");
        }
        return carModelFound.get(0);
    }

    @Override
    public CarModel findCarModelByCarBrandAndModel(String brand, String model) {
        return carModelRepo.findCarModelByCarBrandAndModel(brand,model).orElseThrow(() -> new ResourceNotFound("INVALID_CAR_MODEL","No CarModel found"));
    }
}
