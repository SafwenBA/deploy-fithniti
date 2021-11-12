package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.Country;
import com.team.fithniti.demo.repository.CountryRepo;
import com.team.fithniti.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryRepo countryRepo;

    @Override
    public List<Country> getAll() {
        return countryRepo.findAll();
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("INVALID_COUNTRY", "No country Found")
        );
    }

    @Override
    public Country getCountryByName(String name) {
        return countryRepo.getCountryByName(name);
    }

    @Override
    public Boolean existsCountryByName(String name) {
        return countryRepo.existsCountryByName(name);
    }

    @Override
    public Country create(Country newCountry) {
        if (newCountry == null) {
            throw new InvalidResource(null, "INVALID_COUNTRY", "Can't persist null entity");
        }
        if(existsCountryByName(newCountry.getName())) {
            throw new InvalidResource(null, "INVALID_COUNTRY", "Such country exists already");
        }
        return countryRepo.save(newCountry);
    }

    @Override
    public void updateCountry(Long id, Country updatedCountry) {
        Country countryData = getCountryById(id);
        if(existsCountryByName(updatedCountry.getName()) && updatedCountry.getFlagImageURL().equals(countryData.getFlagImageURL())) {
            throw new InvalidResource(null, "INVALID_COUNTRY", "Such country exists already");
        }
        Country _country = countryData;
        _country.setName(updatedCountry.getName());
        _country.setFlagImageURL(updatedCountry.getFlagImageURL());
        countryRepo.save(_country);
    }

    @Override
    public void deleteCountry(Long id) {
        getCountryById(id);
        countryRepo.deleteById(id);
    }
}
