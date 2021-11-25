package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.RideFilterOption;
import com.team.fithniti.demo.dto.request.NewRide;
import com.team.fithniti.demo.dto.response.RideDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Api("/rides")
public interface RideAPI {
    @ApiOperation(value = "CREATE RIDE",notes = "this method allow to create new ride",response = RideDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE )
    RideDTO create(@RequestBody NewRide ride);

    @ApiOperation(value = "SEARCH RIDE",notes = "this method allow to search rides with custom filter",responseContainer = "Page<RideDTO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<RideDTO> findAll(@RequestBody RideFilterOption options);

    @ApiOperation(value = "SEARCH RIDE BY ID",notes = "this method allow to search rides by its id",response= RideDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    RideDTO findById(@PathVariable Long id);

    @ApiOperation(value = "SEARCH DRIVER RIDES",notes = "this method allow to search driver rides with custom filter",responseContainer = "Page<RideDTO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "/driver/{driverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<RideDTO> findDriverRides(@PathVariable Long driverId,@RequestBody RideFilterOption options);

    @ApiOperation(value = "DELETE RIDE",notes = "this method allow to delete rides",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping(value = "{rideId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> deleteById(@PathVariable Long rideId);

    @ApiOperation(value = "UPDATE RIDE",notes = "this method allow to update rides",response= RideDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/{rideId}")
    RideDTO update(@PathVariable Long rideId, @RequestBody Map<String,Object> changes);

}
