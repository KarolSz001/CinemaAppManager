package com.app.repo.impl;



import com.app.model.SalesStand;
import com.app.repo.generic.AbstractCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalesStandRepositoryImpl extends AbstractCrudRepository<SalesStand, Long> {
    @Autowired

public SalesStandRepositoryImpl() {super();}
}
