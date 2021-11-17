package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CityRepo extends JpaRepository<City, Long> {

    Boolean existsCityByStateNameAndName(String countryStateName, String cityName);

    @Transactional
    @Modifying
    @Query("delete from City c where c.id = ?1")
    void deleteCityById(Long countryStateId);
}
