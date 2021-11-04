package com.team.fithniti.demo.model;

import com.team.fithniti.demo.util.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "app_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser extends Auditable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate birthDate;
    private UserState state;
    private String photoURL;

    @OneToOne
    @JoinColumn(name = "role_id") // default: entity_id --> No need for joinColumn except for specifying != name
    private Role role;



}
