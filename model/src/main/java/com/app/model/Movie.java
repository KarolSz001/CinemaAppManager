package com.app.model;


import com.app.model.enums.Genre;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

@Entity
@Table(name="movies")
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Genre genre;
    private Double price;
    private Integer duration;
    private LocalDate release_date;

    @OneToMany(mappedBy = "movie")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SalesStand> salesStands;

}
