package com.team.fithniti.demo.service;

import com.team.fithniti.demo.model.AppUser;

import java.util.List;

public interface UserService {
    List<AppUser> getAll();
    AppUser create(AppUser user);
}
