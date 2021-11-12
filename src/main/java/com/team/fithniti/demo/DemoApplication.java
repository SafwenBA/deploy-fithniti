package com.team.fithniti.demo;

import com.team.fithniti.demo.dto.request.NewRide;
import com.team.fithniti.demo.dto.response.RideDTO;
import com.team.fithniti.demo.model.*;
import com.team.fithniti.demo.repository.DriverRepo;
import com.team.fithniti.demo.repository.PassengerRepo;
import com.team.fithniti.demo.repository.RideRepo;
import com.team.fithniti.demo.repository.RoleRepo;
import com.team.fithniti.demo.service.RideService;
import com.team.fithniti.demo.util.RideType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private RoleRepo roleRepo;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Autowired
    private RideService rideService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder() ;
    }

    @Override
    public void run(String... args) throws Exception {
        Role user = new Role(1, "USER");
        Role admin = new Role(2, "ADMIN");
        Role superadmin = new Role(3, "SUPER_ADMIN");

        if (!roleRepo.existsById(1)){
            roleRepo.save(user);
        }
        if (!roleRepo.existsById(2)){
            roleRepo.save(admin);
        }
        if (!roleRepo.existsById(3)){
            roleRepo.save(superadmin);
        }

//        RideDTO r1 = rideService.create(NewRide.builder().rideType(RideType.COMFORTABLE)
//                .maxPlaces((byte) 4)
//                .description("desc****")
//                .price(8.5F)
//                .idDriver(?) // complete rest...
//                .build());

    }
}
