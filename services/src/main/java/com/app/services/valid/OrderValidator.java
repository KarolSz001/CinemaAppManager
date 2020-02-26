package com.app.services.valid;

import com.app.model.CustomerOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class OrderValidator extends AbstractValidator<CustomerOrder> {


    @Override
    public Map<String, String> validate(CustomerOrder customerOrder) {
        errors.clear();

        if (!isDateCorrect(customerOrder.getDate())) {
            errors.put("Error nr 1", "Order Data is valid");
        }
        if (!isDiscountNumberCorrect(customerOrder.getDiscount())) {
            errors.put("Error nr 2", "Name is too short");
        }

        return errors;
    }

    private boolean isDateCorrect(LocalDate localDate) {
        return localDate.isEqual(LocalDate.now()) || localDate.isAfter(LocalDate.now());
    }

    private boolean isDiscountNumberCorrect(BigDecimal discount) {
        return discount.compareTo(new BigDecimal(1.0)) < 0 || discount.compareTo(new BigDecimal(0.0)) > 0;

    }



    }



