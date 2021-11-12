package com.team.fithniti.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "cars")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Car extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Car(String brand, String logoURL, List<CarModel> carModels) {
        this.brand = brand;
        this.logoURL = logoURL;
        this.carModels = carModels;
    }

    @Column(unique = true, nullable = false)
    private String brand;

    @Column(name = "logoURL")
    private String logoURL;

    @OneToMany(targetEntity = CarModel.class, mappedBy = "car",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonIgnore
    List<CarModel> carModels;
}
