package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceExists;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.City;
import com.team.fithniti.demo.model.CountryState;
import com.team.fithniti.demo.repository.CityRepo;
import com.team.fithniti.demo.service.CityService;
import com.team.fithniti.demo.service.CountryService;
import com.team.fithniti.demo.service.CountryStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private CountryStateService countryStateService;

    @Autowired
    private CountryService countryService;


    @Override
    public List<City> getAllCities(String countryName, String countryStateName) {
        if (!countryService.existsCountryByName(countryName)) {
            throw new ResourceNotFound("INVALID_COUNTRY", "This country does not exist");
        }
        if(!countryStateService.existsCountryStateByCountryNameAndCountryStateName(countryName, countryStateName)) {
            throw new ResourceNotFound("INVALID_COUNTRY_STATE", "This country state does not exist");
        }
        CountryState countryState = countryStateService.getCountryStateByCountryNameAndName(countryName, countryStateName);
        return countryState.getCities();
    }

    @Override
    public City getCity(Long cityId, String countryName, String countryStateName) {
        List<City> cities = getAllCities(countryName, countryStateName);
        if(cities.size() == 0) {
            throw new InvalidResource(null, "EMPTY_LIST", "No cities found for this country state");
        }
        List<City> cityFound = cities.stream()
                .filter(city -> city.getId() == cityId)
                .collect(Collectors.toList());
        if (cityFound.size() == 0) {
            throw new ResourceNotFound("INVALID_CITY", "No city found for this ID");
        }
        return cityFound.get(0);
    }

    @Override
    public Boolean existsByCountryNameAndStateNameAndCityName(String countryName, String countryStateName, String cityName) {
        if (countryService.existsCountryByName(countryName)) {
            if (countryStateService.existsCountryStateByCountryNameAndCountryStateName(countryName, countryStateName)) {
                return cityRepo.existsCityByStateNameAndName(countryStateName, cityName);
            }
        }
        return false;
    }

    @Override
    public City create(City city, String countryStateName, String countryName) {
        if (city == null) {
            throw new InvalidResource(null, "INVALID_CITY", "Can't persist an empty entity");
        }
        if (!countryService.existsCountryByName(countryName)) {
            throw new InvalidResource(null, "INVALID_COUNTRY", "This country does not exist");
        } else if (!countryStateService.existsCountryStateByCountryNameAndCountryStateName(countryName, countryStateName)) {
            throw new InvalidResource(null, "INVALID_COUNTRY_STATE", "This country state does not exist");
        }
        if (existsByCountryNameAndStateNameAndCityName(countryName, countryStateName, city.getName())) {
            throw new ResourceExists("EXIST", "This city name exists already");
        }
        CountryState countryState = countryStateService.getCountryStateByCountryNameAndName(countryName, countryStateName);
        city.setState(countryState);
        countryState.getCities().add(city);
        return cityRepo.save(city);
    }

    @Override
    public void updateCity(Long cityId, String countryName, String countryStateName, City updatedCity) {
        if (!countryService.existsCountryByName(countryName)) {
            throw new InvalidResource(null, "INVALID_COUNTRY", "This country does not exist");
        } else if (!countryStateService.existsCountryStateByCountryNameAndCountryStateName(countryName, countryStateName)) {
            throw new InvalidResource(null, "INVALID_COUNTRY_STATE", "This country state does not exist");
        }
        if (existsByCountryNameAndStateNameAndCityName(countryName, countryStateName, updatedCity.getName())) {
            throw new ResourceExists("EXIST", "This city name exists already");
        }
        City _city = getCity(cityId, countryName, countryStateName);
        _city.setName(updatedCity.getName());
        cityRepo.save(_city);
    }

    @Override
    public void deleteCity(Long cityId, String countryName, String countryStateName) {
        if (!countryService.existsCountryByName(countryName)) {
            throw new InvalidResource(null, "INVALID_COUNTRY", "This country does not exist");
        } else if (!countryStateService.existsCountryStateByCountryNameAndCountryStateName(countryName, countryStateName)) {
            throw new InvalidResource(null, "INVALID_COUNTRY_STATE", "This country state does not exist");
        }
        City city = getCity(cityId, countryName, countryStateName);
        if (!existsByCountryNameAndStateNameAndCityName(countryName, countryStateName, city.getName())) {
            throw new InvalidResource(null, "INVALID_CITY", "This city doesn't exist");
        }
        cityRepo.deleteCityById(cityId);
    }
}
