package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}
