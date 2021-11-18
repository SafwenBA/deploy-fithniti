package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.service.CountryStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v2")
public class CountryStateController {

    @Autowired
    private CountryStateService countryStateService;

    @GetMapping("/country-states")
    public ResponseEntity<List<CountryState>> getAllCountryStates(@RequestParam(name = "country") String countryName) {
        List<CountryState> countryStates = countryStateService.getAllCountryStates(countryName);
        return new ResponseEntity<>(countryStates, HttpStatus.OK);
    }

    @GetMapping("/country-states/{id}")
    public ResponseEntity<CountryState> getCountryState(@PathVariable("id") Long countryStateId,
                                                        @RequestParam(name = "country") String countryName) {
        CountryState countryState = countryStateService.getCountryState(countryStateId, countryName);
        return new ResponseEntity<>(countryState, HttpStatus.OK);
    }

    @PostMapping("/country-states")
    public ResponseEntity<String> addCountryState(@RequestBody CountryState countryState,
                                                  @RequestParam(name = "country") String countryName) {
        countryStateService.create(countryName, countryState);
        return new ResponseEntity<>("New country State is added", HttpStatus.CREATED);
    }

    @PutMapping("/country-states/{id}")
    public ResponseEntity<String> updateCountryState(@RequestBody CountryState updatedCountryState,
                                                     @RequestParam(name = "country") String countryName,
                                                     @PathVariable("id") Long countryStateId) {
        countryStateService.updateCountryState(countryName, countryStateId, updatedCountryState);
        return new ResponseEntity<>("Country state updated", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/country-states/{id}")
    public ResponseEntity<String> deleteCountryState(@PathVariable("id") Long countryStateId,
                                                     @RequestParam("country") String countryName) {
        countryStateService.deleteCountryState(countryName, countryStateId);
        return new ResponseEntity<>("Country state deleted", HttpStatus.OK);
    }
}
