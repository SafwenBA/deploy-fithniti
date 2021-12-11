package com.team.fithniti.demo.model;

import com.team.fithniti.demo.util.ReportAction;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "handled_reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandledReport extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ReportAction action;
    private String reason;

    @OneToOne
    private Admin admin;

    @OneToOne
    private RideReport rideReport;
}
