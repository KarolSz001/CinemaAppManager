package com.app.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="loyalty_cards")
public class LoyaltyCard {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate expirationDate;
    private Double discount;
    private Integer moviesNumber;
    private Integer current_movies_number;

    @OneToOne(mappedBy = "loyaltyCard")
    private Customer customer;
}
