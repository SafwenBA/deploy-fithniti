package com.team.fithniti.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Passenger houwa eli bech rate ride ---> from ride rating, Driver will be rated
 */
@Entity
@Table(name = "ride_reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RideReview extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;  // TODO: 10/30/21 nb stars : validate from 1 to 5
    private String comment;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinTable(name = "ride_review_types", joinColumns = { @JoinColumn(name = "ride_review_id") },
            inverseJoinColumns = { @JoinColumn(name = "review_type_id") })
    private List<ReviewType> reviewTypes;
    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Ride ride;


}
