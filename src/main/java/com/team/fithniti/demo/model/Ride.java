package com.team.fithniti.demo.model;

import com.team.fithniti.demo.util.RideState;
import com.team.fithniti.demo.util.RideType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "rides")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ride extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Embedded
    private RidePathway pathway;
    private RideType rideType;
    private RideState rideState;
    private Byte availablePlaces;
    private Byte maxPlaces;
    private Byte ratersNumber;
    private Float rating; // average
    private Float price;
    private LocalTime startTime;
    private LocalTime arrivalTime;
    //    no need for StartDate -- auto sysDate ---> stored fi createdDate
    // TODO: 10/30/21 chat group : similar to MS teams

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JoinTable(name = "ride_tags", joinColumns = { @JoinColumn(name = "ride_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    List<Tag> tags;

    @OneToMany(mappedBy = "ride",cascade = CascadeType.ALL)
    List<RideRequest> rideRequests;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<RideReview> rideReviews;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<PassengerReview> passengerReviews;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;





}