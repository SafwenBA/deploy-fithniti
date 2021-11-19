package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.response.UpdateResponse;
import com.team.fithniti.demo.dto.response.WarningDismiss;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.UUID;

public interface UserAPI {
    @ApiOperation(value = "dismissWarning",notes = "this method will let the user dismiss the warning on his account"
            ,responseContainer = "WarningDismiss")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST")
    })
    WarningDismiss dismissWarning(UUID user_id) ;
    @ApiOperation(value = "updateProfile",notes = "this method will let the user update his password or his logoUrl"
            ,responseContainer = "UpdateResponse")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST")
    })
    UpdateResponse updateProfile(UUID userId, String password, String logoUrl) ;
}
