package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.RideRequestFilterOption;
import com.team.fithniti.demo.dto.response.RideRequestDTO;
import com.team.fithniti.demo.util.RideRequestState;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api("/ride-requests")
public interface RideRequestAPI {
    @ApiOperation(value = "CREATE RIDE REQUEST",notes = "this method allow to create new ride request",response = RideRequestDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    RideRequestDTO create(@RequestParam Long passengerId,@RequestParam Long rideId );


    @ApiOperation(value = "CANCEL RIDE REQUEST",notes = "this method allow to cancel ride request",response = RideRequestDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> cancel(Long passengerId, Long rideId );


    @ApiOperation(value = "HANDLE RIDE REQUEST",notes = "this method allow to ***",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping(value = "/handle", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> handle(@RequestParam Long reqId ,@RequestParam  RideRequestState state);


    @ApiOperation(value = "SEARCH RIDE REQUEST",notes = "this method allow to create new ride",responseContainer = "Page<RideRequestDTO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<RideRequestDTO> getRideRequests(@RequestParam Long rideId,@RequestBody RideRequestFilterOption options);


    @ApiOperation(value = "SEARCH PASSENGER RIDE REQUEST",notes = "this method allow ***",responseContainer = "Page<RideRequestDTO>")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "Bad Request: Invalid data"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 403 ,message = "Forbidden: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<RideRequestDTO> getPassengerRequests(@RequestParam Long passengerId,@RequestBody RideRequestFilterOption options);
}
