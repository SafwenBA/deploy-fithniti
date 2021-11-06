package com.team.fithniti.demo.model;

import com.team.fithniti.demo.util.RideRequestState;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ride_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideRequest extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RideRequestState state;

    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Ride ride;


}
