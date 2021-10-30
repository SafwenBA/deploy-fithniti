package com.team.fithniti.demo.model;

import com.team.fithniti.demo.util.RequestState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "phonenumber_update_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberUpdateReq extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String newPhoneNumber;
    private String verificationCode;
    private RequestState state;
    @OneToOne
    private AppUser user;
}
