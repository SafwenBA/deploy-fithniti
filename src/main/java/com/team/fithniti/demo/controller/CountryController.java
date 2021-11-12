package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.model.Country;
import com.team.fithniti.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v2")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.getAll();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable("id") Long id) {
        Country country = countryService.getCountryById(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping("/countries")
    public ResponseEntity<String> addCountry(@RequestBody Country country) {
        countryService.create(country);
        return new ResponseEntity<>("New country is added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<String> updateCountry(@PathVariable("id") Long id, @RequestBody Country country) {
        countryService.updateCountry(id, country);
        return new ResponseEntity<>("Country updated successfully", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Long id) {
        countryService.deleteCountry(id);
        return new ResponseEntity<>("Country deleted successfully", HttpStatus.ACCEPTED);
    }
}
