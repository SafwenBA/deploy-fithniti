package com.team.fithniti.demo.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Api
public interface HelloWorldAPI {

    @ApiOperation(value = "Hello World demo",notes = "this method will Return a List of Strings ",responseContainer = "Map<String>")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 401 ,message = "Unauthorized: Bad credential"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<Object, Object> hello();
}
