package com.team.fithniti.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "drivers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer ridesNumber;// or for each req : count on ride requests...
    private Float rating;// average

    @OneToMany(mappedBy = "driver", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<Ride> rides;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.REMOVE, orphanRemoval = true)
    List<PassengerReview> passengerReviews;

    @OneToOne
    private AppUser user;

    public Driver(AppUser user) {
        this.user = user;
    }
}
