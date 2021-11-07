package com.team.fithniti.demo.model;

import com.team.fithniti.demo.util.RequestState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_registration_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationRequest extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String verificationCode;
    private Integer attemptsNumber; // exp: only 3
    private RequestState requestState;


    @OneToOne
    private AppUser user;



}
