package com.app.services.valid;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractValidator<T> implements Validator<T> {

    protected Map<String, String> errors = new HashMap<>();

    @Override
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
