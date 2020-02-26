package com.app.repo.impl;


import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.ErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorRepositoryImpl extends AbstractCrudRepository<Error, Long> implements ErrorRepository {

    @Autowired

    public ErrorRepositoryImpl() {
        super();
    }

}
