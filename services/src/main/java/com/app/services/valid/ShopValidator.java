package com.app.services.valid;



import com.app.model.Shop;

import java.util.Map;

public class ShopValidator extends AbstractValidator<Shop> {

    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Za-z]+";

    @Override
    public Map<String, String> validate(Shop shop) {
    errors.clear();

        if (!isNameCorrect(shop.getName())) {
            errors.put("Error nr 1", "Name only work with Letters");
        }
        if (!isNameLengthCorrect(shop.getName())) {
            errors.put("Error nr 3", "Name is too short");
        }
        return errors;
    }


    private boolean isNameCorrect(String name) {
        return name.matches(NAME_REGEX);
    }

    private boolean isNameLengthCorrect(String name) {
        return name.length() > MIN_LENGTH_NAME;
    }
}
