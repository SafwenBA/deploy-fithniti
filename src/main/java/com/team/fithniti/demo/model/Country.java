package com.team.fithniti.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "countries")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Country {
    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "countriesSeq", initialValue = 2)
    @GeneratedValue(generator = "mySeqGen")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "flagImageURL")
    private String flagImageURL;

    @OneToMany(targetEntity = CountryState.class, mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonIgnore
    List<CountryState> countryStates;
}
