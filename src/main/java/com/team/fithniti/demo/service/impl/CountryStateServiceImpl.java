package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceExists;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.Country;
import com.team.fithniti.demo.model.CountryState;
import com.team.fithniti.demo.repository.CountryStateRepo;
import com.team.fithniti.demo.service.CountryService;
import com.team.fithniti.demo.service.CountryStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryStateServiceImpl implements CountryStateService {

    @Autowired
    private CountryStateRepo countryStateRepo;

    @Autowired
    private CountryService countryService;

    @Override
    public List<CountryState> getAllCountryStates(String name) {
        if (!countryService.existsCountryByName(name)) {
            throw new ResourceNotFound("INVALID_COUNTRY", "This country doesn't exist");
        }
        Country country = countryService.getCountryByName(name);
        return country.getCountryStates();
    }

    @Override
    public CountryState getCountryState(Long countryStateId, String countryName) {
        if (!countryService.existsCountryByName(countryName)) {
            throw new ResourceNotFound("INVALID_COUNTRY", "This country doesn't exist");
        }
        List<CountryState> countryStates = getAllCountryStates(countryName);
        if(countryStates.size() == 0) {
            throw new InvalidResource(null, "EMPTY_LIST", "This country has no country states");
        }
        List<CountryState> countryStatesFound = countryStates.stream()
                .filter(countryState -> countryState.getId() == countryStateId)
                .collect(Collectors.toList());
        if (countryStatesFound.size() == 0) {
            throw new ResourceNotFound("INVALID_COUNTRY_STATE", "This country state doesn't exist");
        }
        return countryStatesFound.get(0);
    }

    @Override
    public Boolean existsCountryStateByCountryNameAndCountryStateName(String countryName, String countryStateName) {
        return countryStateRepo.existsCountryStateByCountryNameAndName(countryName, countryStateName);
    }

    @Override
    public CountryState create(String countryName, CountryState countryState) {
        if(!countryService.existsCountryByName(countryName)) {
            throw new ResourceNotFound("INVALID_COUNTRY", "This country doesn't exist");
        }
        if(existsCountryStateByCountryNameAndCountryStateName(countryName, countryState.getName())) {
            throw new ResourceExists("EXIST", "This country state name exists already");
        }
        Country country = countryService.getCountryByName(countryName);
        countryState.setCountry(country);
        country.getCountryStates().add(countryState);
        return countryStateRepo.save(countryState);
    }

    @Override
    public CountryState getCountryStateByCountryNameAndName(String countryName, String countryStateName) {
        return countryStateRepo.getCountryStateByCountryNameAndName(countryName, countryStateName);
    }

    @Override
    public void updateCountryState(String countryName, Long countryStateId, CountryState updatedCountryState) {
        if (!countryService.existsCountryByName(countryName)) {
            throw new ResourceNotFound("INVALID_COUNTRY", "This country doesn't exist");
        }
        if(existsCountryStateByCountryNameAndCountryStateName(countryName, updatedCountryState.getName())) {
            throw new InvalidResource(null, "INVALID_COUNTRY_STATE", "This country state already exist");
        }
        CountryState _countryState = getCountryState(countryStateId, countryName);
        _countryState.setName(updatedCountryState.getName());
        countryStateRepo.save(_countryState);
    }

    @Override
    public void deleteCountryState(String countryName, Long countryStateId) {
        countryStateRepo.deleteCountryStateByIdAndCountryName(countryStateId, countryName);
    }
}
