package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Loyalty_Card {
    Integer id;
    LocalDate expirationDate;
    Double discount;
    Integer moviesNumber;
    Integer current_movies_number;
}
