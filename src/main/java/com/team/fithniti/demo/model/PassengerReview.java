package com.team.fithniti.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Driver rate passenger through ride
 */

@Entity
@Table(name = "passanger_reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerReview extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Byte rating;
    private String comment;

    @OneToOne
    private ReviewType reviewType;

    @ManyToOne
    private Driver driver; // rater

    @ManyToOne
    private Passenger passenger; // rated

    @ManyToOne
    private Ride ride;



}
