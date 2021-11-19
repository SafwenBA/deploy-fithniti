package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.UserAPI;
import com.team.fithniti.demo.dto.response.UserDTO;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.repository.UserRepo;
import com.team.fithniti.demo.util.UserState;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Transactional
public class UserController implements UserAPI {
    @Autowired
    private final UserRepo userRepo ;

    //todo - add change state to normal when alerted
    @Override
    public void dismissWarning(UUID user_id) {
        Optional<AppUser> appUser = userRepo.findById(user_id) ;
        if (!appUser.isPresent())
            throw new ResourceNotFound("400","could not find user with given id !") ;
        if (appUser.get().getState().equals(UserState.WARNED))
            appUser.get().setState(UserState.ACTIVE);
    }

    @GetMapping("")
    public List<UserDTO> findAll(){
        return userRepo.findAll().stream().map(UserDTO::fromEntity).collect(Collectors.toList());
    }

    //todo - add logo update & password update




}
