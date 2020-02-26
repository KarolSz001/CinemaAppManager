package com.app.repo;


import com.app.model.Movie;
import com.app.model.MovieWithDateTime;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.model.exception.AppException;


import java.util.List;

public class MovieRepository extends AbstractCrudRepository<Movie, Integer> {


    @Override
    public void add(Movie movie) {

        if (movie == null) {
            throw new AppException(" add wrong argument - > null");
        }

        connection.withHandle(handle ->
                handle.execute("INSERT INTO movie (title, genre, price, duration, release_date) values (?, ?, ?, ?, ?)", movie.getTitle(), movie.getGenre(), movie.getPrice(), movie.getDuration(), movie.getRelease_date()));

    }

    @Override
    public void update(Integer integer, Movie movie) {

        connection.withHandle(handle ->
                handle
                        .createUpdate("UPDATE movie set title = :title, genre = :genre , price = :price, duration = :duration, release_date =: release_date WHERE id = :id")
                        .bind("title", movie.getTitle())
                        .bind("genre", movie.getGenre())
                        .bind("price", movie.getPrice())
                        .bind("duration", movie.getDuration())
                        .bind("dddd", movie.getRelease_date())
                        .execute()
        );

    }


    public List<MovieWithDateTime> getInfo() {
        return connection.withHandle(handle ->
                handle.createQuery("select ss.id, mm.title, ss.start_date_time, mm.price, mm.genre, mm.duration, cc.name, cc.surname, cc.email " +
                        "FROM sales_stand ss JOIN movie mm " +
                        "ON ss.movie_id = mm.id " +
                        "INNER JOIN customer cc " +
                        "ON cc.id = ss.customer_id;")
                        .mapToBean(MovieWithDateTime.class)
                        .list()
        );
    }

}
