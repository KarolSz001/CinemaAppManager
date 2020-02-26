package com.app.repo;



import com.app.model.Sales_Stand;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.model.exception.AppException;

public class SalesStandRepository extends AbstractCrudRepository<Sales_Stand, Integer> {


    @Override
    public void add(Sales_Stand item) {
        if(item == null){
            throw new AppException(" add wrong argument - > null");
        }

        connection.withHandle(handle ->
                handle.execute("INSERT INTO sales_stand (customer_id, movie_id, start_date_time, discount) values (?, ?, ?, ?)", item.getCustomerId(), item.getMovieId(), item.getStart_date_time(), item.getDiscount()));

    }

    @Override
    public void update(Integer integer, Sales_Stand item) {

    }







}
