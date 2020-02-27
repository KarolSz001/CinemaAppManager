package com.app.model;


import com.app.model.enums.Genre;
import lombok.*;

import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Movie {

    Integer id;
    String title;
    Genre genre;
    Double price;
    Integer duration;
    LocalDate release_date;

    @OneToMany(mappedBy = "movie")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SalesStand> salesStands;

}
