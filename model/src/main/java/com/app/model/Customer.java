package com.app.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="customers")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surname;
    private Integer age;
    private String email;
    private Long loyaltyCardNumber;



    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SalesStand> salesStands;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "loyaltycard_id", unique = true)
    private LoyaltyCard loyaltyCard;

}
