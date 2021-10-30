package com.team.fithniti.demo.model;

import com.team.fithniti.demo.util.RequestState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "password_recovery_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecoveryReq extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String verificationCode;
    private RequestState state;

    @OneToOne
    private AppUser user;

}
