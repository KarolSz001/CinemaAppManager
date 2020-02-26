package com.app.services;

import com.app.exception.AppException;
import com.app.model.Country;
import com.app.repo.generic.CountryRepository;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.CountryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class CountryService {


    CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    private Country addCountryToDB(Country country) {

        if (country == null) {
            throw new AppException("OBJECT IS NULL");
        }
        return countryRepository.addOrUpdate(country)
                .orElseThrow(() -> new AppException("CANNOT INSERT COUNTRY"));
    }

    public void createCountriesInDb() {

        System.out.println("LOADING AUTOFILL PROGRAM TO UPDATE DATA_BASE");
        List<Country> countries = List.of(
                Country.builder().name("POLAND").build(),
                Country.builder().name("GERMAN").build(),
                Country.builder().name("UK").build(),
                Country.builder().name("USA").build()
        );
        for (Country country : countries) {
            Optional<Country> countryByName = countryRepository.findByName(country.getName());
            if (countryByName.isEmpty()) {
                addCountryToDB(country);
            }
        }
    }


    protected Country findRandomCountry() {
        List<Country> countries = countryRepository.findAll();
        int index = new Random().nextInt(countries.size() - 1);
        return countries.get(index);
    }

    public void printAllRecordsInCountries() {

        System.out.println(" \n LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        countryRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    public Country add(Country country){
        if( country == null){
            throw new AppException("NULL ARG");
        }
        if (country.getName() == null) {
            throw new IllegalArgumentException("name is null");
        }
        return countryRepository.addOrUpdate(country).orElseThrow(()->new AppException("NO RECORDS"));
    }


    public void countryInit() {
        String answer = DataManager.getLine("WELCOME TO COUNTRY DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA MANUALLY OR N IF YOU WANNA FILL THEM IN AUTOMATE");
        if (answer.toUpperCase().equals("Y")) {
            countryDataInitManualFill();
        } else {
            countryDataInitAutoFill();
        }
    }

    public void countryDataInitAutoFill() {
        createCountriesInDb();
        printAllRecordsInCountries();
    }

    private void countryDataInitManualFill() {
        System.out.println("\n LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF RECORD YOU WANNA ADD TO DB");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleCountryRecordCreator();
        }
        printAllRecordsInCountries();
    }

    private void singleCountryRecordCreator() {
        CountryValidator countryValidator = new CountryValidator();
        String name = DataManager.getLine("PRESS COUNTRY NAME");
        Country country = Country.builder().name(name).build();
        countryValidator.validate(country);

        if (countryValidator.hasErrors()) {
            throw new AppException("VALID DATA IN COUNTRY CREATOR");
        } else {
            addCountryToDB(country);
        }
    }

    public Country findCountryById(Long id) {
        return countryRepository.findOne(id).orElseThrow(() -> new AppException("THERE IS NO RECORDS IN DB"));
    }


    public Country findCountryByName(String name) {
        return countryRepository.findByName(name).orElseThrow(() -> new AppException("THERE IS NO RECORDS IN DB"));
    }

    public List<Country> findAll(){
        return countryRepository.findAll();
    }

    public Country findRandomCountryFromDB() {
        List<Country> countries = countryRepository.findAll();
        if (countries.size() == 0) {
            throw new AppException("THERE IS NO RECORDS IN DB");
        } else
            return countries.get(new Random().nextInt(countries.size()));
    }

    public void clearDataFromCountry() {
        countryRepository.deleteAll();
    }


}
