package com.team.fithniti.demo.model;


import lombok.*;

import javax.persistence.*;

@Table(name = "cities")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class City {

    @Id
    @SequenceGenerator(name = "mySeqGen", sequenceName = "citiesSeq", initialValue = 260)
    @GeneratedValue(generator = "mySeqGen")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private CountryState state;

}
