package com.app.repo.generic;


import com.app.model.exception.AppException;
import com.app.repo.connect.DbConnect;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;


public abstract class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {

    protected Jdbi connection = DbConnect.getInstance().getConnection();

    private final Class<T> type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];


    @Override
    public void delete(ID id) {

        if (id == null) {
            throw new AppException("delete - id is null");
        }

        connection.withHandle(handle -> handle
                .createUpdate("delete from " + type.getSimpleName().toLowerCase() + " where id = :id")
                .bind("id", id)
                .execute()
        );
    }

    @Override
    public Optional<T> findOne(ID id) {

        if (id == null) {
            throw new AppException(" find one - id is null ");
        }

        return connection.withHandle(handle -> handle
                .createQuery("select * from " + type.getSimpleName().toLowerCase() + " where id = :id")
                .bind("id", id)
                .mapToBean(type)
                .findFirst()
        );

    }

    @Override
    public List<T> findAll() {

        return connection.withHandle(handle -> handle
                .createQuery("select * from " + type.getSimpleName().toLowerCase())
                .mapToBean(type)
                .list()
        );
    }


}
