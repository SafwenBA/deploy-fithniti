package com.team.fithniti.demo.service;

import com.team.fithniti.demo.model.Country;

import java.util.List;

public interface CountryService {
    List<Country> getAll();
    Country getCountryById(Long id);
    Country getCountryByName(String name);
    Boolean existsCountryByName(String name);
    Country create(Country country);
    void updateCountry(Long id, Country country);
    void deleteCountry(Long id);
}
