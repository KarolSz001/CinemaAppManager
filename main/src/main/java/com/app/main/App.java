package com.app.main;


import com.app.model.valid.CustomerValidator;
import com.app.repo.CustomerRepository;
import com.app.repo.LoyaltyCardRepository;
import com.app.repo.MovieRepository;
import com.app.repo.SalesStandRepository;
import com.app.services.ControlAppService;
import com.app.services.CustomerService;
import com.app.services.MovieService;
import com.app.services.SaleTicketService;

public class App {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        sb.append(" ----------------------------------------------------------------------------- \n");
        sb.append(" CinemaAppManager v1.0 25.02.2020 \n ");
        sb.append(" Karol Szot \n");
        sb.append(" ----------------------------------------------------------------------------- \n");
        System.out.println(sb.toString());

        var movieRepository = new MovieRepository();
        var customerRepository = new CustomerRepository();
        var customerValidator = new CustomerValidator();
        var salesStandRepository = new SalesStandRepository();
        var loyaltyCardRepository = new LoyaltyCardRepository();
        var saleTicketService = new SaleTicketService();

        var customerService = new CustomerService(
                movieRepository, customerRepository, customerValidator, salesStandRepository, loyaltyCardRepository);
        var movieService = new MovieService(customerRepository, customerValidator, salesStandRepository, loyaltyCardRepository, movieRepository);

        var controlAppService = new ControlAppService(customerService,movieService,saleTicketService);
        controlAppService.controlLoop();
    }
}
