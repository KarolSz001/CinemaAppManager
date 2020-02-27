package com.app.repo.impl;


import com.app.model.Movie;
import com.app.model.MovieWithDateTime;
import com.app.repo.generic.AbstractCrudRepository;



import java.util.List;

public class MovieRepositoryImpl extends AbstractCrudRepository<Movie, Integer> {



    public List<MovieWithDateTime> getInfo() {
       /* return connection.withHandle(handle ->
                handle.createQuery("select ss.id, mm.title, ss.start_date_time, mm.price, mm.genre, mm.duration, cc.name, cc.surname, cc.email " +
                        "FROM sales_stand ss JOIN movie mm " +
                        "ON ss.movie_id = mm.id " +
                        "INNER JOIN customer cc " +
                        "ON cc.id = ss.customer_id;")
                        .mapToBean(MovieWithDateTime.class)
                        .list()
        );*/
       return null;
    }

}
