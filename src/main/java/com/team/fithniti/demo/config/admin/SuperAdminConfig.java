package com.team.fithniti.demo.config.admin;

import com.team.fithniti.demo.dto.request.NewAdmin;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.Role;
import com.team.fithniti.demo.repository.RoleRepo;
import com.team.fithniti.demo.repository.UserRepo;
import com.team.fithniti.demo.service.FlickrService;
import com.team.fithniti.demo.util.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class SuperAdminConfig {

    @Autowired
    private FlickrService flickrService;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Bean
    public void initSuperAdmin(){
        Optional<AppUser> admin = userRepo.findByPhoneNumber("+21627943044");
        if(admin.isPresent()){
            return;
        }

        NewAdmin superAdmin = new NewAdmin("+21627943044", "18rombatakaya#?",
                "safwen", "the nigga");
        AppUser appUser = superAdmin.convertToAppUser() ;
        appUser.setLastConnectedAs(UserType.Passenger);
        if(appUser.getPhotoUrl() == null || appUser.getPhotoUrl().equals("")){
            // set default logo
            appUser.setPhotoUrl(flickrService.getDefaultLogo());
            //todo - uncomment this when image service is active
            //appUser.setPhotoUrl(imageService.getDefaultLogo());
        }
        Optional<Role> userRole = roleRepo.getRoleByName("SUPER_ADMIN") ;
        userRole.ifPresent(role -> appUser.setRole(userRole.get()));
        userRepo.save(appUser);
        System.out.println("Super Admin created");
    }

}
