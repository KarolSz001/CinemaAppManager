package com.app.services;

import com.app.exception.AppException;
import com.app.model.Country;
import com.app.model.Shop;
import com.app.repo.generic.ShopRepository;
import com.app.service.dataUtility.DataManager;
import com.app.service.valid.ShopValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
public class ShopService {

    private final ShopRepository shopRepository;
    private final CountryService countryService;

    @Autowired
    public ShopService(ShopRepository shopRepository, CountryService countryService) {
        this.shopRepository = shopRepository;
        this.countryService = countryService;
    }

    public Shop addShopToDB(Shop shop) {
        if (shop == null) {
            throw new AppException("object is null");
        }
        shopRepository.addOrUpdate(shop);
        return shop;
    }

    private void generateShopsInDB() {

        List<String> shopNames = List.of("BIEDRA", "ZABA", "TESCO");

        for (String shopName : shopNames) {
            Country country = countryService.findRandomCountryFromDB();
            Shop shop = Shop.builder().name(shopName).country(country).build();
            Optional<Shop> shopByName = shopRepository.findByName(shopName);
            if (shopByName.isEmpty())
                addShopToDB(shop);
        }
    }

    public void shopInit() {

        String answer = DataManager.getLine("WELCOME TO SHOP DATA PANEL GENERATOR PRESS Y IF YOU WANNA PRESS DATA MANUALLY OR N IF YOU WANNA FILL THEM IN AUTOMATE");
        if (answer.toUpperCase().equals("Y")) {
            categoryDataShopInitManualFill();
        } else {
            shopDataInitAutoFill();
        }
    }

    public void printAllShopRecordsInDB() {
        System.out.println("LOADING DATA COMPLETED ----> BELOW ALL RECORDS");
        shopRepository.findAll().forEach((s) -> System.out.println(s + "\n"));
    }

    public void shopDataInitAutoFill() {
        generateShopsInDB();
        printAllShopRecordsInDB();
    }

    private void categoryDataShopInitManualFill() {
        System.out.println("LOADING MANUAL PROGRAM TO UPDATE DATA_BASE");
        int numberOfRecords = DataManager.getInt("PRESS NUMBER OF RECORD YOU WANNA ADD TO DB");

        for (int i = 1; i <= numberOfRecords; i++) {
            singleShopRecordCreator();
        }

        printAllShopRecordsInDB();
    }

    private Shop singleShopRecordCreator() {
        ShopValidator shopValidator = new ShopValidator();

        String name = DataManager.getLine("PRESS SHOP NAME");
        Country country = countryService.findRandomCountryFromDB();
        Shop shop = Shop.builder().name(name).country(country).build();

        shopValidator.validate(shop);
        if (shopValidator.hasErrors()) {
            throw new AppException("ERROR IN SHOP VALIDATION");
        }

        Optional<Shop> shopByName = shopRepository.findByName(name);
        if (shopByName.isEmpty()) {
            addShopToDB(shop);
        }
        return shop;
    }


    public Shop findShopById(Long id) {
        return shopRepository.findOne(id).orElseThrow(() -> new AppException("NO RECORD FOUND"));
    }

    public Shop findRandomShopFromDb() {
        List<Shop> shops = shopRepository.findAll();
        return shops.get(new Random().nextInt(shops.size()));
    }

    public void clearDataFromShop() {
        shopRepository.deleteAll();
    }

}
