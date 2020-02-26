package com.app.services;



import com.app.model.Customer;
import com.app.model.Loyalty_Card;
import com.app.model.MovieWithDateTime;
import com.app.model.exception.AppException;
import com.app.model.valid.CustomerValidator;
import com.app.repo.CustomerRepository;
import com.app.repo.LoyaltyCardRepository;
import com.app.repo.MovieRepository;
import com.app.repo.SalesStandRepository;
import com.app.services.dataGenerator.DataGenerator;
import com.app.services.dataGenerator.DataManager;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerService {

    private static final Integer DISCOUNT_LIMIT = 1;

    private final MovieRepository movieRepository;
    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private final SalesStandRepository salesStandRepository;
    private final LoyaltyCardRepository loyaltyCardRepository;

    public CustomerService(
            MovieRepository movieRepository,
            CustomerRepository customerRepository,
            CustomerValidator customerValidator,
            SalesStandRepository salesStandRepository,
            LoyaltyCardRepository loyaltyCardRepository) {
        this.movieRepository = movieRepository;
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
        this.salesStandRepository = salesStandRepository;
        this.loyaltyCardRepository = loyaltyCardRepository;
    }

    public void addCustomer(Customer customer)  {
        if (customer == null) {
            throw new AppException("customer is null");
        }
        if (validationCustomerBeforeAdd2(customer)) {
            customerRepository.add(customer);
        } else throw new AppException(" wrong validationCustomerBeforeAdd  ");
    }

    private boolean validationCustomerBeforeAdd2(Customer customer) throws AppException {
        CustomerValidator customerValidator = new CustomerValidator();
        return customerValidator.isValidate(customer) && (!isEmailAlreadyExist(customer.getEmail()));
    }


    public void removeCustomerById(Integer id) {
        if (id == null) {
            throw new AppException("null id number");
        }
        customerRepository.delete(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Integer customerId) {
        return Optional.of(customerRepository.findOne(customerId).orElseThrow(() -> new AppException(" No customer found")));
    }

    public Optional<Customer> getCustomerByEmail(String customerEmail) {
        return Optional.of(findAll().stream().filter(f -> f.getEmail().equals(customerEmail)).findFirst()).orElseThrow(() -> new AppException(" No customer found"));
    }

    private boolean isEmailAlreadyExist(String email) throws AppException {
        return getCustomerByEmail(email).isPresent();
    }

    public void updateCustomer(Customer customer) {
        customerRepository.update(customer.getId(), customer);
    }

    public boolean isCardAvailable(Integer customerId) {
        if (!hasLoyalCard(customerId)) {
            return salesStandRepository.findAll().stream().filter(f -> f.getCustomerId().equals(customerId)).count() >= DISCOUNT_LIMIT;
        }
        return false;
    }

    public boolean hasLoyalCard(Integer customerId) {
        return customerRepository.findOne(customerId).get().getLoyalty_card_id() != null;
    }

    public void addIdLoyalCardToCustomer(Integer idCard, Integer customerId) {
        customerRepository.addIdLoyaltyCardToCustomer(idCard, customerId);
    }

    public boolean isCardActive(Integer customerId) {
        Integer idCard = customerRepository.findOne(customerId).get().getLoyalty_card_id();
        if (isCardActiveByNumberOfMovies(idCard) && isCardActiveByDate(idCard)) {
            System.out.println(" CARD IS STILL ACTIVE ");
            return true;
        } else {
            System.out.println(" CARD LOST ACTIVATION ");
            return false;
        }
    }

    private boolean isCardActiveByNumberOfMovies(Integer idCard) {
        return getCardById(idCard).getCurrent_movies_number() < getCardById(idCard).getMoviesNumber();
    }

    private boolean isCardActiveByDate(Integer idCard) {
        return !LocalDate.now().isAfter(getCardById(idCard).getExpirationDate());
    }

    private Loyalty_Card getCardById(Integer id) {
        return loyaltyCardRepository.findOne(id).get();
    }

    public Customer getCustomerOperation() throws AppException {

        System.out.println(" PLEASE GIVE YOU EMAIL TO CHECK IF YOU ARE IN DATABASE ");
        String email = DataManager.getLine(" GIVE EMAIL ");
        Customer customer;
        System.out.println(" CHECKING DATABASE BY EMAIL CUSTOMER ");

        if (getCustomerByEmail(email).isPresent()) {
            System.out.println(" CUSTOMER AVAILABLE ");
            customer = getCustomerByEmail(email).get();

        } else {
            System.out.println(" NO CUSTOMER IN DATABASE , LET'S CREATE ONE ");
            customer = singleCustomerCreator();
            if (getCustomerByEmail(email).isPresent()) {
                throw new AppException(" EMAIL IS ALREADY EXIST IN DATABASE ");
            }
            if (!validationCustomerBeforeAdd2(customer)) {
                throw new AppException(" VALIDATION CUSTOMER ERROR ");
            }

        }

        System.out.println(" CREATED CUSTOMER ---->>>>> " + customer);
        return customer;
    }

    private Customer singleCustomerCreator() {
        String name = DataManager.getLine(" GIVE YOU NAME ").toUpperCase();
        String surname = DataManager.getLine(" GIVE YOU SURNAME ").toUpperCase();
        Integer age = DataManager.getInt(" GIVE YOU AGE ");
        String email = DataManager.getLine(" GIVE YOU EMAIL ");
        return Customer.builder().name(name).surname(surname).age(age).email(email).build();

    }

    public void customerGeneratorDate() throws AppException {
        DataGenerator.customersGenerator().stream().peek(this::addCustomer).forEach(this::printFormattedCustomer);
    }

    public void editCustomerById() throws AppException {
        Customer customer = getCustomerById(DataManager.getInt(" PRESS ID CUSTOMER ")).get();
        updateCustomer(customer);
    }

    public void removeCustomerById() throws AppException {
        removeCustomerById(DataManager.getInt(" PRESS ID CUSTOMER "));
    }


    public void printAllCustomers() {
        if (isCustomerBaseEmpty()) {
            System.out.println(" DATABASE IS EMPTY \n");
        } else {
            findAll().forEach(this::printFormattedCustomer);

        }
    }

    private void printFormattedCustomer(Customer s) {
        System.out.println("-----------------------------------------------------------------------------\n");
        System.out.printf("%5s %15s %25s %15s %15s", "CUSTOMER ID", "NAME", "SURNAME", "AGE", "EMAIL", "Card_Id \n");
        System.out.format("\n %5s %20s %25s %15s %20s \n", s.getId(), s.getName(), s.getSurname(), s.getAge(), s.getEmail(), s.getLoyalty_card_id());
        System.out.println("-----------------------------------------------------------------------------\n");

    }

    public Customer creatCustomer() {
        String name = DataManager.getLine(" GIVE A NAME ");
        String surname = DataManager.getLine(" GIVE SURNAME ");
        Integer age = DataManager.getInt(" GIVE AGE ");
        String email = DataManager.getLine(" GIVE EMAIL ");
        return new Customer().builder().id(null).name(name).surname(surname).age(age).email(email).build();
    }

    boolean isCustomerBaseEmpty() {
        return findAll().isEmpty();
    }


    public List<Customer> getAllCustomersWithLoyaltyCard() {
        return findAll().stream().filter(f -> f.getLoyalty_card_id() != null).peek(System.out::println).collect(Collectors.toList());
    }

    public void sortCustomerBySurname() {
        findAll().stream().sorted((s1, s2) -> s2.getSurname().compareTo(s1.getSurname())).forEach(this::printFormattedCustomer);
    }

    public void printCustomersByNumbersWatchedMovies() {
        movieRepository.getInfo().stream()
                .collect(Collectors.groupingBy(MovieWithDateTime::getEmail))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().map(MovieWithDateTime::getTitle).count()
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(System.out::println);
    }

}


