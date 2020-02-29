package com.app.services;


import com.app.model.Customer;
import com.app.model.LoyaltyCard;
import com.app.model.MovieWithDateTime;
import com.app.model.SalesStand;
import com.app.model.enums.Genre;
import com.app.model.exception.AppException;
import com.app.model.valid.CustomerValidator;
import com.app.repo.impl.CustomerRepositoryImpl;
import com.app.repo.impl.LoyaltyCardRepositoryImpl;
import com.app.repo.impl.MovieRepositoryImpl;
import com.app.repo.impl.SalesStandRepositoryImpl;
import com.app.services.dataGenerator.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class SaleTicketService {

    private final MovieRepositoryImpl movieRepositoryimpl = new MovieRepositoryImpl();
    private final CustomerRepositoryImpl customerRepositoryImpl = new CustomerRepositoryImpl();
//    private final CustomerValidator customerValidator = new CustomerValidator();
    private final SalesStandRepositoryImpl salesStandRepositoryImpl = new SalesStandRepositoryImpl();
    private final LoyaltyCardRepositoryImpl loyaltyCardRepositoryImpl = new LoyaltyCardRepositoryImpl();
    private final MovieService movieService = new MovieService( movieRepositoryimpl);
    private final CustomerService customerService = new CustomerService(movieRepositoryimpl, customerRepositoryImpl, salesStandRepositoryImpl, loyaltyCardRepositoryImpl);

    private static final LocalTime HIGH_RANGE_TIME = LocalTime.of(22, 30);
    private static final Integer MOVIES_LIMIT_NUMBER = 2;
    private static final Double DISCOUNT_VALUE = 8.0;

    @Autowired
    public SaleTicketService() {
    }

    /**
     * Main method of sailing Tickets for customer , include :
     * - movieService.showAllMoviesToday();
     * - movieService.showMovieById(idMovie);
     * - getCustomerByEmail();
     * - getDataTime();
     * - addTicketToDataBase();
     * - discountPriceTicket(movieWithDateTime);
     * - addLoyalty(customer)
     */

    public void saleTicketOperation(Customer customer) throws AppException {
        if (customer == null) {
            throw new AppException(" null arg in saleTicketOperation method");
        }
        MovieWithDateTime movieWithDateTime;
        SalesStand sales_stand;
        customerService.addCustomer(customer);
        DataManager.getLine(" PRESS KEY TO CONTINUE TO SEE WHAT WE HAVE TODAY TO WATCH ");
        System.out.println(" BELOW MOVIES WHICH ARE AVAILABLE TODAY ");
        movieService.showAllMoviesToday();
        Long idMovie = DataManager.getLong(" PRESS ID MOVIE NUMBER AS YOU CHOICE ");
        movieService.showMovieById(idMovie);
        Long customerId = customerService.getCustomerByEmail(customer.getEmail()).get().getId();
        LocalDateTime dateTime = getDataTime();

        if (isCardAvailableForCustomer(customerId)) {
            boolean isConfirmation = DataManager.getBoolean(" DO YOU WANNA GET LOYAL CARD ?? ");
            if (isConfirmation) {
                addLoyalty(customer);
            }
        }
        if (hasDiscount(customerId)) {
            System.out.println(" DISCOUNT IS ACTIVE ");
            sales_stand = new SalesStand().builder().customerId(customerId).movieId(idMovie).start_date_time(dateTime).discount(true).build();
            addTicketToDataBase(sales_stand);
            movieWithDateTime = sendConfirmationOfSellingTicket(customer.getEmail());
            discountPriceTicket(movieWithDateTime);
            increaseCurrentNumberMovieInLoyalCard(customerId);
        } else {
            System.out.println(" DISCOUNT IS NOT ACTIVE ");
            sales_stand = new SalesStand().builder().customerId(customerId).movieId(idMovie).start_date_time(dateTime).discount(false).build();
            addTicketToDataBase(sales_stand);
            movieWithDateTime = sendConfirmationOfSellingTicket(customer.getEmail());
        }
        System.out.println(" SEND CONFIRMATION OF SELLING TICKET -----> \n" + movieWithDateTime);
    }

    private LocalDateTime getDataTime() {
        LocalDate date = LocalDate.now();
        LocalTime time = getScreeningHours();
        return LocalDateTime.of(date, time);
    }

    private LocalTime getScreeningHours() {
        System.out.println("\n SCREENING HOURS ...... ");
        printAvailableTime();
        Integer hh = DataManager.getInt("\n give a hour ");
        Integer mm = DataManager.getInt(" give minutes ");
        if (isTimeOverRange(LocalTime.of(hh, mm))) {
            System.out.println(" IT IS INCORRECT TIME, YOU WILL GET LAST POSSIBLE SCREEN HOUR ");
            return HIGH_RANGE_TIME;
        }
        return correctTime(LocalTime.of(hh, mm));
    }

    private LocalTime correctTime(LocalTime localTime) {
        if (localTime == null) {
            throw new AppException(" null arg in correctTime method");
        }
        LocalTime lt = LocalTime.parse(localTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        if (lt.getMinute() <= 30 && lt.getMinute() > 0 && lt.getHour() <= 22) {
            return LocalTime.of(lt.getHour(), 30);
        }
        if (lt.getMinute() > 30 && lt.getHour() <= 21) {
            return LocalTime.of(lt.getHour() + 1, 0);
        }
        return LocalTime.of(lt.getHour(), 0);
    }

    private boolean isTimeOverRange(LocalTime localTime) {
        return localTime.isAfter(HIGH_RANGE_TIME) || localTime.isBefore(LocalTime.now());
    }

    private void discountPriceTicket(MovieWithDateTime item) {
        DecimalFormatSymbols otherSymbol = new DecimalFormatSymbols(Locale.getDefault());
        DecimalFormat dc = new DecimalFormat("#.##", otherSymbol);
        Double priceAfterDiscount = Double.valueOf(dc.format(item.getPrice() - (DISCOUNT_VALUE * 0.01 * item.getPrice())));
        item.setPrice(priceAfterDiscount);
    }

    private boolean hasDiscount(Long customerId) {
        if (!customerService.hasLoyalCard(customerId)) {
            return false;
        } else {
            return isLoyalCardActive(customerId);
        }
    }

    private boolean isCardAvailableForCustomer(Long customerId) {
        return customerService.isCardAvailable(customerId);
    }

    private boolean isLoyalCardActive(Long customerId) {
        return customerService.isCardActive(customerId);
    }

    private MovieWithDateTime sendConfirmationOfSellingTicket(String customerEmail) {
        return movieService.getInfo().stream().filter(f -> f.getEmail().equals(customerEmail)).max(Comparator.comparing(MovieWithDateTime::getId)).get();
    }

    private void addTicketToDataBase(SalesStand ss) {
        salesStandRepositoryImpl.addOrUpdate(ss);
    }

    private void addLoyalty(Customer customer) {

        Long customerId = customerService.getCustomerByEmail(customer.getEmail()).get().getId();
        LocalDate date = LocalDate.now().plusMonths(1);
        LoyaltyCard loyaltyCard = new LoyaltyCard().builder().expirationDate(date).discount(DISCOUNT_VALUE).moviesNumber(MOVIES_LIMIT_NUMBER).current_movies_number(0).build();
        loyaltyCardRepositoryImpl.addOrUpdate(loyaltyCard);
        int sizeOfCardList = loyaltyCardRepositoryImpl.findAll().size();
        // get last added card
        Long idLoyaltyCard = loyaltyCardRepositoryImpl.findAll().get(sizeOfCardList - 1).getId();
        customerService.addIdLoyalCardToCustomer(idLoyaltyCard, customerId);
        System.out.println(" ADDED NEW LOYALTY_CARD FOR CUSTOMER \n");
    }

    private void printAvailableTime() {
        LocalTime counter = correctTime(LocalTime.now());
        while (counter.isBefore(HIGH_RANGE_TIME)) {
            System.out.print(counter + " || ");
            counter = counter.plusMinutes(30);
        }
    }

    private void increaseCurrentNumberMovieInLoyalCard(Long itemId) {
        Long loyaltyCardId = customerService.getCustomerById(itemId).get().getLoyaltyCardNumber();
        LoyaltyCard loyaltyCard = loyaltyCardRepositoryImpl.findOne(loyaltyCardId).get();
        int number = loyaltyCard.getCurrent_movies_number() + 1;
        loyaltyCard.setCurrent_movies_number(number);
        loyaltyCardRepositoryImpl.addOrUpdate(loyaltyCard);
    }

    public List<MovieWithDateTime> printAllTicketsHistory(Customer customer) {
        if (customer == null) {
            throw new AppException(" null arg in printAllTicketsHistory method");
        }
        return movieService.getInfo().stream().filter(f -> f.getEmail().equals(customer.getEmail())).collect(Collectors.toList());
    }

    public List<MovieWithDateTime> filterAllTicketsHistoryByGenre(Customer customer) {
        if (customer == null) {
            throw new AppException(" null arg in filterAllTicketsHistoryByGenre method");
        }
        Genre genre = Genre.valueOf(DataManager.getLine(" GIVE A GENRE  - > ACTION, HORROR, FANTASY, DRAMA, COMEDY ").toUpperCase());
       return  movieService.getInfo().stream().filter(f -> f.getEmail().equals(customer.getEmail())).filter(f -> f.getGenre().equals(genre)).collect(Collectors.toList());
    }

    public List<MovieWithDateTime> filterAllTicketsHistoryByMaxDurationTime(Customer customer) {
        if (customer == null) {
            throw new AppException(" null arg in filterAllTicketsHistoryByMaxDurationTime method");
        }
        Integer duration = DataManager.getInt(" GIVE MAX DURATION TIME ");
       return  movieService.getInfo().stream().filter(f -> f.getEmail().equals(customer.getEmail())).filter(f -> f.getDuration().equals(duration)).collect(Collectors.toList());
    }


}
