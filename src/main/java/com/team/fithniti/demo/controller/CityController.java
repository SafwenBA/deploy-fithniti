package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.model.City;
import com.team.fithniti.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v2")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities(@RequestParam("country") String countryName,
                                                   @RequestParam("state") String countryStateName) {
        List<City> cities = cityService.getAllCities(countryName, countryStateName);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<City> getCity(@RequestParam("country") String countryName,
                                        @RequestParam("state") String countryStateName,
                                        @PathVariable("id") Long cityId) {
        City city = cityService.getCity(cityId, countryName, countryStateName);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PostMapping("/cities")
    public ResponseEntity<String> addCity(@RequestParam("country") String countryName,
                                          @RequestParam("state") String countryStateName,
                                          @RequestBody City city) {
        cityService.create(city, countryStateName, countryName);
        return new ResponseEntity<>("New city is added", HttpStatus.CREATED);
    }

    @PutMapping("/cities/{id}")
    public ResponseEntity<String> updateCity(@RequestParam("country") String countryName,
                                             @RequestParam("state") String countryStateName,
                                             @RequestBody City city,
                                             @PathVariable("id") Long cityId) {
        cityService.updateCity(cityId, countryName, countryStateName, city);
        return new ResponseEntity<>("City updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/cities/{id}")
    public ResponseEntity<String> deleteCity(@RequestParam("country") String countryName,
                                             @RequestParam("state") String countryStateName,
                                             @PathVariable("id") Long cityId) {
        cityService.deleteCity(cityId, countryName, countryStateName);
        return new ResponseEntity<>("City deleted successfully", HttpStatus.OK);
    }
}
