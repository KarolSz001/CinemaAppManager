package com.app.repo.impl;


import com.app.model.Customer;
import com.app.repo.generic.AbstractCrudRepository;


public class CustomerRepositoryImpl extends AbstractCrudRepository<Customer, Long> {


    public void addIdLoyaltyCardToCustomer(Long idCard, Long customerId){
        /*connection.withHandle(handle ->
                handle
                .createUpdate("UPDATE customer set loyalty_card_id = :loyalty_card_id WHERE id = :id;")
                .bind("loyalty_card_id", idCard)
                .bind("id", customerId)
                .execute()
        );*/
    }


    public void removeLoyaltyCard(Long customerId){
       /* connection.withHandle(handle ->
                handle
                        .createUpdate("UPDATE customer set loyalty_card_id = :loyalty_card_id WHERE id = :id;")
                        .bind("id", customerId)
                        .bind("loyalty_card-id", 0)
                        .execute()
        );*/
    }



}
