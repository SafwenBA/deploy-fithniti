package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> getRoleByName(String name) ;
}
