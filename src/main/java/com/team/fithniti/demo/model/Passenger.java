package com.team.fithniti.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "passengers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer ridesNumber;// or for each req : count on ride requests...
    private Float rating;// the average

    @OneToMany(mappedBy = "passenger",cascade = CascadeType.ALL)
    List<RideRequest> rideRequests ;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<RideReview> rideReviews;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<PassengerReview> passengerReviews;

    
    @OneToOne
    private AppUser user;

    public Passenger(AppUser user) {
        this.user = user;
    }
}
