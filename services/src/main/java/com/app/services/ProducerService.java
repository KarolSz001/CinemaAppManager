package com.app.services;

import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Producer;
import com.app.model.Trade;
import com.app.repo.generic.ProducerRepository;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.ProducerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final TradeService tradeService;
    private final CountryService countryService;
    @Autowired
    public ProducerService(ProducerRepository producerRepository, TradeService tradeService, CountryService countryService) {
        this.producerRepository = producerRepository;
        this.tradeService = tradeService;
        this.countryService = countryService;
    }

    public Producer addProducerToDB(Producer producer) {
        if (producer == null) {
            throw new AppException("object is null");
        }
        return producerRepository.addOrUpdate(producer).orElseThrow(() -> new AppException("NO RECORD IN DB"));
    }

    private boolean isProducerAlreadyInDB(String producerName) {
        return !producerRepository.findByName(producerName).isEmpty();
    }


    public void generateProducersInDB() {
        List<String> producerNames = List.of("DANONE", "IBM", "MOTOROLA", "TREC");

        for (String name : producerNames) {
            Country country = countryService.findRandomCountry();
            Trade trade = tradeService.findRandomTradeInDB();
            Producer producer = Producer.builder().name(name).country(country).trade(trade).build();
            if (!isProducerAlreadyInDB(name))
                addProducerToDB(producer);
        }
    }

    public void producerInit() {
        String answer = DataManager.getLine("WELCOME TO PRODUCER DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA MANUALLY OR N IF YOU WANNA FILL THEM IN AUTOMATE");
        if (answer.toUpperCase().equals("Y")) {
            producerInitManual();
        } else {
            producerInitAuto();
        }
    }

    public void printProducerRecordsFromDB() {
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        producerRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    public void producerInitAuto() {
        generateProducersInDB();
        printProducerRecordsFromDB();
    }

    private void producerInitManual() {
        System.out.println("LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF PRODUCERS YOU WANNA ADD TO DB");
        for (int i = 1; i <= numberOfRecords; i++) {
            singleProducerRecordCreator();
        }
        printProducerRecordsFromDB();
    }

    private Producer singleProducerRecordCreator() {
        ProducerValidator producerValidator = new ProducerValidator();
        String producerName = DataManager.getLine("PRESS PRODUCER NAME");
        Country country = countryService.findRandomCountryFromDB();
        Trade trade;
        try {
            trade = tradeService.findTradeByName(DataManager.getLine("PRESS TRADE NAME"));
        } catch (AppException e) {
            System.out.println(" NO RECORDS OF TRADE NAME , RETURN RANDOM VALUE");
            trade = tradeService.findRandomTradeInDB();
        }
        Producer producer = Producer.builder().name(producerName).country(country).trade(trade).build();

        producerValidator.validate(producer);
        if (producerValidator.hasErrors()) {
            throw new AppException("ERROR IN TRADE VALIDATION");
        }

        Optional<Producer> producerByName = producerRepository.findByName(producerName);
        if (producerByName.isEmpty()) {
            addProducerToDB(producer);
        }
        return producer;
    }

    public void printAllRecordsInProducers() {
        producerRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    public Producer findRandomProducerFromDb() {
        List<Producer> producers = producerRepository.findAll();
        return producers.get(new Random().nextInt(producers.size()));
    }

    public void clearDataFromProducer() {
        producerRepository.deleteAll();
    }


}
