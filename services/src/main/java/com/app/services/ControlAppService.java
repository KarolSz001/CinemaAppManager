package com.app.services;


import com.app.model.Customer;
import com.app.model.Movie;
import com.app.services.dataGenerator.DataManager;
import com.app.model.exception.AppException;
import com.app.services.screen.MenuPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ControlAppService {

    private CustomerService customerService;
    private MovieService movieService;
    private SaleTicketService saleTicketService;

    @Autowired
    public ControlAppService(CustomerService customerService, MovieService movieService, SaleTicketService saleTicketService) {
        this.customerService = customerService;
        this.movieService = movieService;
        this.saleTicketService = saleTicketService;
    }

    public void controlLoop() {
        try {
            boolean loopOn = true;
            while (loopOn) {

                MenuPrinter.startMenu();
                Integer read = DataManager.getInt(" PRESS NUMBER TO MAKE A CHOICE ");

                switch (read) {
                    case 0: {
                        MenuPrinter.printExit();
                        clearDb();
                        return;
                    }
                    case 1: {
                        customerOperation();
                        break;
                    }
                    case 2: {
                        moviesOperation();
                        break;
                    }
                    case 3: {
                        saleOperation();
                        break;
                    }
                    case 4: {
                        statOperation();
                        break;
                    }
                }
            }
        } catch (AppException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    public void moviesOperation() throws AppException {
        while (true) {
            MenuPrinter.printMoviesMenu();
            System.out.println();
            Integer choice = DataManager.getInt(" PRESS NUMBER TO MAKE A CHOICE ");

            switch (choice) {
                case 0: {
                    return;
                }
                case 1: {
                    movieService.showAllMoviesToday();
                    break;
                }
                case 2: {
                    Long id = DataManager.getLong(" PRESS ID MOVIE NUMBER ");
                    movieService.showMovieById(id);
                    break;
                }
                case 3: {
                    movieService.removeMovieById();
                    break;
                }
                case 4: {
                    Movie movie = movieService.createMovie();
                    movieService.addMovie(movie);
                    break;
                }
                case 5: {
                    movieService.sortMoviesByDurationTime();
                    break;
                }

                case 6: {
                    movieService.editMovieById();
                    break;
                }
            }
        }
    }

    public void statOperation() throws AppException {
        while (true) {
            System.out.println(" WELCOME TO STATIC MENU \n");
            MenuPrinter.printStatisticMenu();
            Integer choice = DataManager.getInt(" PRESS NUMBER TO MAKE A CHOICE ");

            switch (choice) {
                case 0: {
                    return;
                }
                case 1: {
                    movieService.printStatisticByMoviePrice();
                    break;
                }
                case 2: {
                    movieService.printStatisticByDurationTime();
                    break;
                }
                case 3: {
                    movieService.printMapOfGenreAndNumberMovies();
                    break;
                }
            }
        }
    }

    public void saleOperation() throws AppException {
        while (true) {
            System.out.println(" WELCOME TO SERVICE TICKETS SERVICES ");
            Customer customer = customerService.getCustomerOperation();
            MenuPrinter.printSaleTicketServiceMenu();
            System.out.println();
            Integer choice = DataManager.getInt(" PRESS NUMBER TO MAKE A CHOICE ");

            switch (choice) {
                case 0: {
                    return;
                }
                case 1: {
                    saleTicketService.saleTicketOperation(customer);
                    break;
                }
                case 2: {
                    System.out.println(" SEND ALL SALE TICKETS CUSTOMER HISTORY ");
                    saleTicketService.printAllTicketsHistory(customer).forEach(System.out::println);
                    break;
                }
                case 3: {
                    System.out.println(" SEND ALL SALE TICKETS CUSTOMER HISTORY WITH CHOOSE GENRE ");
                    saleTicketService.filterAllTicketsHistoryByGenre(customer).forEach(System.out::println);
                    break;
                }
                case 4: {
                    System.out.println(" SEND ALL SALE TICKETS CUSTOMER HISTORY WITH CHOOSE MAX DURATION TIME ");
                    saleTicketService.filterAllTicketsHistoryByMaxDurationTime(customer).forEach(System.out::println);
                    break;
                }
            }
        }
    }

    public void customerOperation() throws AppException {

        while (true) {
            MenuPrinter.printCustomersMenu();
            System.out.println();
            Integer readCustomer = DataManager.getInt(" PRESS NUMBER TO MAKE A CHOICE ");

            switch (readCustomer) {

                case 0: {
                    return;
                }
                case 1: {
                    customerService.printAllCustomers();
                    break;
                }
                case 2: {
                    Long customerId = DataManager.getLong(" GIVE CUSTOMER ID ");
                    customerService.getCustomerById(customerId);
                    break;
                }
                case 3: {
                    customerService.removeCustomerById();
                    break;
                }
                case 4: {
                    Customer customer = customerService.creatCustomer();
                    customerService.addCustomer(customer);
                    break;
                }
                case 5: {
                    customerService.editCustomerById();
                    break;
                }
                case 6: {
                    customerService.customerGeneratorDate();
                    break;
                }
                case 7: {
                    customerService.sortCustomerBySurname();
                    break;
                }
                case 8: {
                    customerService.getAllCustomersWithLoyaltyCard();
                    break;
                }
                case 9: {
                    customerService.printCustomersByNumbersWatchedMovies();
                    break;
                }
            }
        }
    }

    private void clearDb(){
        System.out.println("CLEAR DB -------------------------- IN PROGRESS");
        saleTicketService.clearDataSale();
        movieService.clearDataMovie();
        customerService.clearDataCustomer();


    }


}
