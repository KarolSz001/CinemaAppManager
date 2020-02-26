package com.app.services.valid;


import com.app.model.Customer;

import java.util.Map;

public class CustomerValidator extends AbstractValidator<Customer> {
    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Za-z]+";
    private final String SURNAME_REGEX = "[A-Za-z]+";

    @Override

    public Map<String, String> validate(Customer customer) {
        errors.clear();

        if (!isNameCorrect(customer.getName())) {
            errors.put("Error nr 1", "Name only work with Letters");
        }
        if (!isSurnameCorrect(customer.getSurname())) {
            errors.put("Error nr 2", "Surname only work with Letters");
        }
        if (!isNameLengthCorrect(customer.getName())) {
            errors.put("Error nr 3", "Name is too short");
        }

        if (!isSurnameLengthCorrect(customer.getSurname())) {
            errors.put("Error nr 4", "Surname is too short");
        }
        if (!isAgeCorrect(customer.getAge())) {
            errors.put("Error nr 5", "Age is wrong");
        }

        return errors;
    }

    private boolean isNameCorrect(String name) {
        return name.matches(NAME_REGEX);
    }

    private boolean isSurnameCorrect(String name) {
        return name.matches(SURNAME_REGEX);
    }

    private boolean isNameLengthCorrect(String name) {
        return name.length() > MIN_LENGTH_NAME;
    }

    private boolean isSurnameLengthCorrect(String surname) {
        return surname.length() > MIN_LENGTH_NAME;
    }

    private boolean isAgeCorrect(Integer age) {
        return age >= 18;
    }


}
