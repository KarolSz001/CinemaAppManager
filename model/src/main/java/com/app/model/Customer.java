package com.app.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    Integer id;
    String name;
    String surname;
    Integer age;
    String email;
    Integer loyalty_card_id;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SalesStand> salesStands;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "loyalty_card_id", unique = true)
    private LoyaltyCard loyaltyCard;

}
