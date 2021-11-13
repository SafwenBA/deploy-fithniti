package com.team.fithniti.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReportCard {
    //this card used for admin to see list of user and their report counter
    //private UserDTO user;
    private String firstName;
    private String lastName;
    private String photoURL;
    private String phoneNumber;
    private int nbrReport;
}
