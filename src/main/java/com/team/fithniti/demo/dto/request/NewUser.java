package com.team.fithniti.demo.dto.request;

import com.team.fithniti.demo.util.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

/**
 * Only required fields for registration
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUser {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String role;
}
