package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.UserAPI;
import com.team.fithniti.demo.dto.response.UpdateResponse;
import com.team.fithniti.demo.dto.response.WarningDismiss;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.repository.UserRepo;
import com.team.fithniti.demo.service.UserService;
import com.team.fithniti.demo.util.UserState;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Transactional
public class UserController implements UserAPI {

    @Autowired
    private final UserService userService ;

    @Override
    @PostMapping("/dismiss")
    public WarningDismiss dismissWarning(@RequestParam UUID user_id) {
        return userService.dismissWarning(user_id) ;
    }

    @Override
    @PutMapping("/update")
    public UpdateResponse updateProfile(@RequestParam(required = false)UUID userId,
                                        @RequestParam(required = false) String newPassword,
                                        @RequestParam(required = false) String newLogoUrl) {
        return userService.updateProfile(userId, newPassword, newLogoUrl) ;
    }





}
