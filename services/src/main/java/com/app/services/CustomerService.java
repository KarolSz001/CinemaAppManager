package com.app.services;

import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Customer;
import com.app.repo.generic.CustomerRepository;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class CustomerService {

    private final CountryService countryService;
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CountryService countryService, CustomerRepository customerRepository) {
        this.countryService = countryService;
        this.customerRepository = customerRepository;

    }

    public Customer addCustomerToDB(Customer customer) {

        if (customer == null) {
            throw new AppException("object is null");
        }
        return customerRepository.addOrUpdate(customer)
                .orElseThrow(() -> new AppException("cannot insert customer"));
    }

    public Customer singleCountryRecordCreator() {

        CustomerValidator customerValidator = new CustomerValidator();
        String name = DataManager.getLine("PRESS NAME");
        String sureName = DataManager.getLine("PRESS SURNAME");
        Integer age = DataManager.getInt("PRESS AGE");
        Country country = countryService.findRandomCountry();

        Customer customer = Customer.builder().age(age).name(name).surname(sureName).country(country).build();
        customerValidator.validate(customer);
        if (customerValidator.hasErrors()) {
            throw new AppException("ERROR IN CUSTOMER VALIDATION");
        }

        Optional<Customer> customerBySurname = customerRepository.findByName(sureName);
        if (customerBySurname.isPresent() && customerBySurname.get().getName().equals(customer.getName())) {
            throw new AppException("THERE IS RECORD WITH SIMILAR NAME AND SURNAME IN DB");
        }

        return addCustomerToDB(customer);
    }

    public void showAllCustomersInDB() {
        customerRepository.findAll().forEach((s)-> System.out.println("\n" + s));
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findOne(id).orElseThrow(() -> new AppException("THERE IS RECORD IN DB"));

    }

    public Customer findRandomCustomerFromDb() {
        List<Customer> customers = customerRepository.findAll();
        System.out.println(customers);
        return customers.get(new Random().nextInt(customers.size() - 1));
    }

    public void customerInit() {
        String answer = DataManager.getLine("WELCOME TO CUSTOMER DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA MANUALLY OR N IF YOU WANNA FILL THEM IN AUTOMATE");
        if (answer.toUpperCase().equals("Y")) {
            customerDataInitManualFill();
        } else {
            customerDataInitAutoFill();
        }
    }

    public void customerDataInitAutoFill() {
        createCustomerInDbAuto();
        printAllCustomersFormDb();
    }

    private void createCustomerInDbAuto() {
        for (int i = 1; i <= 3; i++) {
            Integer age = ageGenerator();
            String name = nameGenerator();
            String sureName = surNameGenerator();
            Country country = countryService.findRandomCountry();
            Customer customer = Customer.builder().age(age).name(name).surname(sureName).country(country).build();
            addCustomerToDB(customer);
        }

    }

    private Integer ageGenerator() {
        return new Random().nextInt(100 - 18) + 18;
    }

    private String nameGenerator() {
        List<String> names = List.of("KAROL", "ANGELA", "RAFAL", "OLA");
        return names.get(new Random().nextInt(names.size()));
    }

    private String surNameGenerator() {
        List<String> names = List.of("KOWALSKI", "SZOT", "CICHON", "SKRZAT");
        return names.get(new Random().nextInt(names.size()));
    }


    public void customerDataInitManualFill() {
        System.out.println("LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF RECORD YOU WANNA ADD TO DB");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleCountryRecordCreator();
        }
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        printAllCustomersFormDb();
    }

    public void printAllCustomersFormDb() {
        customerRepository.findAll().forEach((s -> System.out.println(s + "\n")));
    }

    public void clearDataFromCustomer() {
        customerRepository.deleteAll();
    }

}

