package com.team.fithniti.demo.service;

import java.util.List;

public interface CountryStateService {
    List<CountryState> getAllCountryStates(String name);
    CountryState getCountryState(Long countryStateId, String name);
    Boolean existsCountryStateByCountryNameAndCountryStateName(String countryName, String countryStateName);
    CountryState create(String countryName, CountryState countryState);
    CountryState getCountryStateByCountryNameAndName(String countryName, String countryStateName);
    void updateCountryState(String countryName, Long id, CountryState countryState);
    void deleteCountryState(String countryName, Long id);
}
