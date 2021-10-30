package com.team.fithniti.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "review_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewType extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    // TODO: 10/30/21 ManayToMany with RideReview...


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "reviewTypes")
    List<RideReview> reviews;
    // TODO: 10/30/21 add extra fields...
}
