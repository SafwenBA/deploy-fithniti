package com.team.fithniti.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="tags")
@NoArgsConstructor
@Data
public class Tag extends Auditable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String tagName;
    public Tag(String tagName) {
        this.tagName = tagName;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "tags")
    private List<Ride> rides;
}