package com.team.fithniti.demo.service;

import com.team.fithniti.demo.model.City;

import java.util.List;

public interface CityService {
    List<City> getAllCities(String countryName, String countryStateName);
    City getCity(Long cityId, String countryName, String countryStateName);
    Boolean existsByCountryNameAndStateNameAndCityName(String countryName, String CountryStateName, String cityName);
    City create(City city, String countryStateName, String countryName);
    void updateCity(Long cityId, String countryName, String countryStateName, City updatedCity);
    void deleteCity(Long cityId, String countryName, String countryStateName);
}
