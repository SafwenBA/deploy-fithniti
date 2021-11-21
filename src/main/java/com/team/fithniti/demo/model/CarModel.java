package com.team.fithniti.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "car_models")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarModel {
    @Id
    @SequenceGenerator(name = "myCarModelSeqGen", sequenceName = "carModelSeq", initialValue = 3549)
    @GeneratedValue(generator = "myCarModelSeqGen")
    private Long id;

    @Column(name = "model", unique = true, nullable = false)
    private String model;

    @Column(name = "imageURL")
    private String imageURL;

    @ManyToOne
    @JoinColumn(name="car_id", referencedColumnName = "id")
    @JsonIgnore
    private Car car;

    public CarModel(String model, String imageURL, Car car) {
        this.model = model;
        this.imageURL = imageURL;
        this.car = car;
    }
}
