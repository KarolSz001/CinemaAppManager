package com.app.repo.impl;


import com.app.model.Payment;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepositoryImpl extends AbstractCrudRepository<Payment, Long> implements PaymentRepository {

    @Autowired

    public PaymentRepositoryImpl() {
        super();
    }
}
