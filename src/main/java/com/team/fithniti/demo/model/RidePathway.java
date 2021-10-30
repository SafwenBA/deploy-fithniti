package com.team.fithniti.demo.model;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class RidePathway {
    @OneToOne
    private Country fromCountry;
    @OneToOne
    private Governorate fromGovernorate;
    @OneToOne
    private City fromCity;

    @OneToOne
    private Country toCountry;
    @OneToOne
    private Governorate toGovernorate;
    @OneToOne
    private City toCity;
}
