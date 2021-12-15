package com.team.fithniti.demo.config.admin;

import com.team.fithniti.demo.dto.request.NewAdmin;
import com.team.fithniti.demo.dto.response.RegistrationSuccessful;
import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceExists;
import com.team.fithniti.demo.model.Admin;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.Role;
import com.team.fithniti.demo.repository.AdminRepo;
import com.team.fithniti.demo.repository.RoleRepo;
import com.team.fithniti.demo.repository.UserRepo;
import com.team.fithniti.demo.service.AdminService;
import com.team.fithniti.demo.service.FlickrService;
import com.team.fithniti.demo.util.UserType;
import com.team.fithniti.demo.validator.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class SuperAdminConfig {

    @Autowired
    private FlickrService flickrService;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepo adminRepo;

    @Bean
    public RegistrationSuccessful initSuperAdmin(){
        NewAdmin admin =new NewAdmin("+21627624966", "007001aa", "Safwen", "Ben abdessalem");

        List<String> errors = UserValidation.validateAdmin(admin);

        if (!errors.isEmpty())
            throw new InvalidResource(errors, "BAD_REQUEST","Invalid user !");
        boolean exists = userRepo.existsAppUserByPhoneNumber(admin.getPhoneNumber()) ;
        if (exists){
            //throw new ResourceExists("FOUND","Phone Number Exists Try another !") ;
            return null;
        }
        AppUser appUser = admin.convertToAppUser() ;
        if(appUser.getPhotoUrl() == null || appUser.getPhotoUrl().equals("")){
            // set default logo
            appUser.setPhotoUrl(flickrService.getDefaultLogo());
        }
        Optional<Role> userRole = roleRepo.getRoleByName("ADMIN") ;
        userRole.ifPresent(appUser::setRole);
        userRepo.save(appUser) ;
        adminRepo.save(Admin.builder()
                .user(appUser)
                .build()) ;
        return new RegistrationSuccessful(appUser,"Admin Account has been created Successfully ! ") ;

//        Optional<AppUser> admin = userRepo.findByPhoneNumber("+21627943044");
//        if(admin.isPresent()){
//            return;
//        }
//
//        NewAdmin superAdmin = new NewAdmin("+21627943044", "18rombatakaya#?",
//                "safwen", "the nigga");
//        AppUser appUser = superAdmin.convertToAppUser() ;
//        appUser.setLastConnectedAs(UserType.Passenger);
//        if(appUser.getPhotoUrl() == null || appUser.getPhotoUrl().equals("")){
//            // set default logo
//            appUser.setPhotoUrl(flickrService.getDefaultLogo());
//            //todo - uncomment this when image service is active
//            //appUser.setPhotoUrl(imageService.getDefaultLogo());
//        }
//        Optional<Role> userRole = roleRepo.getRoleByName("SUPER_ADMIN") ;
//        userRole.ifPresent(role -> appUser.setRole(userRole.get()));
//        userRepo.save(appUser);
//        System.out.println("Super Admin created");
    }

}
