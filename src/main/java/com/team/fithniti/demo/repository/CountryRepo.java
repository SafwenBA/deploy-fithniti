package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country, Long> {

    Country getCountryByName(String name);
    Boolean existsCountryByName(String name);
}
