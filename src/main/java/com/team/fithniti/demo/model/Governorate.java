package com.team.fithniti.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "governorates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Governorate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "governorate", cascade = CascadeType.ALL, orphanRemoval = true)
    List<City> cities;

    @ManyToOne
    private Country country;
}