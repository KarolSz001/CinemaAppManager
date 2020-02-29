package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="salestands")
public class SalesStand {
    @Id
    @GeneratedValue
    private Long id;

    private Long customerId;
    private Long movieId;
    private LocalDateTime start_date_time;
    private Boolean discount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movie_id", unique = true)
    private Movie movie;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id", unique = true)
    private Customer customer;
}
