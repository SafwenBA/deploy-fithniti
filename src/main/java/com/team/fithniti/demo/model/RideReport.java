package com.team.fithniti.demo.model;

import com.team.fithniti.demo.util.ReportedBy;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ride_reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * for reporting Driver and Passenger,
 */
public class RideReport extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ReportedBy reportedBy;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinTable(name = "ride_report_types", joinColumns = { @JoinColumn(name = "ride_report_id") },
            inverseJoinColumns = { @JoinColumn(name = "report_type_id") })
    private List<ReportType> reportTypes;

    @OneToOne
    private Ride ride;

    @OneToOne
    private Passenger passenger;
}
