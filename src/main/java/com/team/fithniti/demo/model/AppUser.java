package com.team.fithniti.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.fithniti.demo.util.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "app_users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser extends Auditable implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private UserState state;
    private String encodedLogo ;
    private boolean confirmed ;

    @OneToOne
    @JoinColumn(name = "role_id") // default: entity_id --> No need for joinColumn except for specifying != name
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authories = new ArrayList<>() ;
        authories.add(new SimpleGrantedAuthority(role.getName())) ;
        return authories;
    }


    @Override
    public String getUsername() {
        return getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return confirmed ;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return state==UserState.ACTIVE;
    }

}
