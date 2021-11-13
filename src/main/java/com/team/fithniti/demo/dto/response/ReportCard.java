package com.team.fithniti.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReportCard {
    //this card used for admin to see list of user and their report counter
    private UserDTO user;
    private int nbrReport;
    //TODO: see if we may add something
}
