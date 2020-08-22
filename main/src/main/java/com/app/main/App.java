package com.app.main;


import com.app.services.ControlAppService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class App {

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        sb.append(" ----------------------------------------------------------------------------- \n");
        sb.append(" CinemaAppManager v1.0 25.02.2020 \n ");
        sb.append(" Karol Szot \n");
        sb.append(" ----------------------------------------------------------------------------- \n");
        System.out.println(sb.toString());


        /*var customerValidator = new CustomerValidator();

        var movieRepository = new MovieRepositoryImpl();
        var customerRepository = new CustomerRepositoryImpl();
        var loyaltyCardRepository = new LoyaltyCardRepositoryImpl();
        var salesStandRepository = new SalesStandRepositoryImpl();

        var saleTicketService = new SaleTicketService();

        var customerService = new CustomerService(
                movieRepository, customerRepository, salesStandRepository, loyaltyCardRepository);
        var movieService = new MovieService(customerRepository, customerValidator, salesStandRepository, loyaltyCardRepository, movieRepository);*/

//        var controlAppService = new ControlAppService(customerService,movieService,saleTicketService);

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        var controlAppService =  context.getBean(ControlAppService.class);
        controlAppService.controlLoop();

    }
}
