package com.app.services.valid;



import com.app.model.Producer;

import java.util.Map;

public class ProducerValidator extends AbstractValidator<Producer> {


    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Za-z]+";

    @Override
    public Map<String, String> validate(Producer producer) {
        errors.clear();

        if (!isNameCorrect(producer.getName())) {
            errors.put("Error nr 1", "Name only work with Letters");
        }
        if (!isNameLengthCorrect(producer.getName())) {
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
