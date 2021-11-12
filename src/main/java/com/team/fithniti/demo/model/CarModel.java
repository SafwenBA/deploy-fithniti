package com.team.fithniti.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "car_models")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarModel extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model", unique = true, nullable = false)
    private String model;

    @Column(name = "imageURL")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name="car_id", referencedColumnName = "id")
    private Car car;
}
