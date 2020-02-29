package com.app.repo.impl;



import com.app.model.LoyaltyCard;
import com.app.repo.generic.AbstractCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LoyaltyCardRepositoryImpl extends AbstractCrudRepository<LoyaltyCard, Long> {
    @Autowired
    public LoyaltyCardRepositoryImpl () {
        super();
    }

}
