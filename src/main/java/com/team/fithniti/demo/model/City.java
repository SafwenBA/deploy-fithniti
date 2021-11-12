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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private CountryState state;

}
