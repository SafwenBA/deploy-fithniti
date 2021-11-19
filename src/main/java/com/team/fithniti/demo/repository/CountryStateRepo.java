package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.CountryState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CountryStateRepo extends JpaRepository<CountryState, Long> {
    Boolean existsCountryStateByCountryNameAndName(String countryName, String countryStateName);

    CountryState getCountryStateByCountryNameAndName(String countryName, String countryStateName);

    @Transactional
    @Modifying
    @Query("delete from CountryState cs where cs.id = ?1")
    void deleteCountryStateByIdAndCountryName(Long countryStateId, String countryName);
}
