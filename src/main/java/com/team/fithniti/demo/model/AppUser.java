package com.team.fithniti.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.fithniti.demo.util.AppUserRole;
import com.team.fithniti.demo.util.UserState;
import com.team.fithniti.demo.util.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "app_users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser extends Auditable implements UserDetails {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private UUID id;

    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate birthDate;
    private UserState state;
    private String photoUrl ;
    private boolean confirmed  ;
    private UserType lastConnectedAs ;
    private int alertsCount ;

    @OneToOne
    @JoinColumn(name = "role_id") // default: entity_id --> No need for joinColumn except for specifying != name
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(AppUserRole.USER.name());
        return Collections.singletonList(simpleGrantedAuthority);
    }


    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
