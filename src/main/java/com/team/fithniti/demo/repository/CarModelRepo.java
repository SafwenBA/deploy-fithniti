package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CarModelRepo extends JpaRepository<CarModel, Long> {

    @Transactional
    @Modifying
    @Query("delete from CarModel cm where cm.id = ?1")
    void deleteCarModelById(Long carModelId, String brand);

    //This method check if a car model exists for a given brand or no
    Boolean existsCarModelByCarBrandAndModel(String brand, String model);
    Optional<CarModel> findCarModelByCarBrandAndModel(String brand, String model);
}
