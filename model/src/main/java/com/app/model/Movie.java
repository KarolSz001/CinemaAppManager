package com.app.model;


import com.app.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


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

}
