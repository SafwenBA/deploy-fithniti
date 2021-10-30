package com.team.fithniti.demo.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "report_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "reportTypes")
    List<RideReport> reports;
}
