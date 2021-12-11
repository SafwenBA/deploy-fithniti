package com.team.fithniti.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
@Data
public class RidePathway {
    @OneToOne
    private Country fromCountry;
    @OneToOne
    private CountryState fromGovernorate;
    @OneToOne
    private City fromCity;

    @OneToOne
    private Country toCountry;
    @OneToOne
    private CountryState toGovernorate;
    @OneToOne
    private City toCity;
}
