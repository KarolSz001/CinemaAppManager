package com.app.model;

import com.app.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieWithDateTime {

    private Long id;
    private String title;
    private LocalDateTime start_date_time;
    private Genre genre;
    private Integer duration;
    private Double price;
    private String name;
    private String surname;
    private String email;


}
