package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.repository.RoleRepo;
import com.team.fithniti.demo.repository.UserRepo;
import com.team.fithniti.demo.service.UserService;
import  com.team.fithniti.demo.validator.UserValidation;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public List<AppUser> getAll() {
        return userRepo.findAll();
    }

    @Override
    public AppUser create(AppUser user) {
        List<String> errors = UserValidation.validate(user);
        // TODO: 10/29/21 data validation + complete the rest
        if (!errors.isEmpty())
            throw new InvalidResource(errors, "BAD_REQUEST","Invalid user !");
        return userRepo.save(user);
    }

}
