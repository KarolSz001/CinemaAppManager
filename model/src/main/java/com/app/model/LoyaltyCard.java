package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoyaltyCard {
    Integer id;
    LocalDate expirationDate;
    Double discount;
    Integer moviesNumber;
    Integer current_movies_number;

    @OneToOne(mappedBy = "license")
    private Customer customer;
}
