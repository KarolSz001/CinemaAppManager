package com.app.model.valid;



import com.app.model.Customer;
import com.app.model.exception.AppException;

public class CustomerValidator {

    private final int MIN_LENGTH_NAME = 2;
    private final String NAME_REGEX = "[A-Za-z]+";
    private final String EMAIL_REGEX = "[A-Za-z]+\\.[A-Za-z]+@(gmail.com|onet.pl|wp.pl|interia.pl|o2.pl)";

    public CustomerValidator() {
    }

    public boolean isValidate(Customer customer)  {

        if (!isNameCorrect(customer.getName())) {
            throw new AppException(" Name only work with Letters");
        }

        if (!isSurnameCorrect(customer.getSurname())) {
            throw new AppException("Surname only work with Letters");
        }
        if (!isNameLengthCorrect(customer.getName()))
            throw new AppException(" Name is too short ");

        if (!isSurnameLengthCorrect(customer.getSurname())) {
            throw new AppException(" Surname is too short ");
        }
        if (!isAgeCorrect(customer.getAge())) {
            throw new AppException(" Age is wrong ");
        }
        if (!isEmailCorrect(customer.getEmail())) {
            throw new AppException(" email is wrong ");
        }
        return true;
    }

    private boolean isNameCorrect(String name) { return name.matches(NAME_REGEX); }

    private boolean isSurnameCorrect(String name) {
        return name.matches(NAME_REGEX);
    }

    private boolean isNameLengthCorrect(String name) {
        return name.length() > MIN_LENGTH_NAME;
    }

    private boolean isSurnameLengthCorrect(String surname) {
        return surname.length() > MIN_LENGTH_NAME;
    }

    private boolean isAgeCorrect(Integer age){
        return age > 0;
    }

    private boolean isEmailCorrect(String email){
        return email.matches(EMAIL_REGEX);
    }


    }
