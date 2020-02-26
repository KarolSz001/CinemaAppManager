package com.app.repo;



import com.app.model.Loyalty_Card;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.model.exception.AppException;


public class LoyaltyCardRepository extends AbstractCrudRepository<Loyalty_Card, Integer> {


    @Override
    public void add(Loyalty_Card item) {
        if (item == null) {
            throw new AppException(" add wrong argument - > null");
        }

        connection.withHandle(handle ->
                handle.execute("INSERT INTO loyalty_card (expiration_date, discount, movies_number, current_movies_number) values (?, ?, ?, ?)", item.getExpirationDate(), item.getDiscount(), item.getMoviesNumber(), item.getCurrent_movies_number()));

    }

    @Override
    public void update(Integer integer, Loyalty_Card item) {
        if (integer == null || item == null) {
            throw new AppException(" add wrong argument - > null");
        }

        connection.withHandle(handle ->
                handle.createUpdate("UPDATE loyalty_card set expiration_date = :expiration_date, discount = :discount , movies_number = :movies_number, current_movies_number = :current_movies_number WHERE id = :id;")
                        .bind("expiration_date",item.getExpirationDate())
                        .bind("discount",item.getDiscount())
                        .bind("movies_number",item.getMoviesNumber())
                        .bind("current_movies_number", item.getCurrent_movies_number())
                        .bind("id", integer)
                        .execute()
        );
    }
}
