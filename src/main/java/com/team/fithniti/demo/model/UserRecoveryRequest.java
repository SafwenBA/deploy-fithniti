package com.team.fithniti.demo.model;

import com.team.fithniti.demo.util.RequestState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_recovery_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRecoveryRequest extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recoveryCode;

    @OneToOne
    private AppUser user;



}
