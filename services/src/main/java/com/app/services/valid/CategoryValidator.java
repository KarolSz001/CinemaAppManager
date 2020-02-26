package com.app.services.valid;

import com.app.model.Category;

import java.util.Map;

public class CategoryValidator extends AbstractValidator<Category> {

    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Z]+";


    @Override
    public Map<String, String> validate(Category category) {
        errors.clear();

        if (!isNameCorrect(category.getName())) {
            errors.put("Error nr 1", "Name only work with UpCase Letters");
        }
        if (!isNameLengthCorrect(category.getName())) {
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
