package com.team.fithniti.demo.service;

import com.team.fithniti.demo.model.CarModel;

import java.util.List;

public interface CarModelService {
    CarModel create(CarModel newCarModel, String brand);
    Boolean existsCarModelByCarBrandAndModel(String brand, String model);
    void update(String brand,Long id, CarModel carModel);
    void delete(Long carModelId, String brand);
    List<CarModel> getAllModels(String brand);
    CarModel getCarModel(Long carModelId, String brand);
    CarModel findCarModelByCarBrandAndModel(String brand, String model);
}
