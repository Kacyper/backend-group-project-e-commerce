package com.kodilla.ecommerce.validator;

import com.kodilla.ecommerce.exception.IllegalPasswordFormatException;
import org.springframework.stereotype.Service;
import java.util.function.Predicate;

@Service
public class PasswordFormatValidator implements Predicate<String> {
    @Override
    public boolean test(final String password) {
        return password != null && password.length() > 5;
    }
    public void validate(final String password)
            throws IllegalPasswordFormatException {
        if (!test(password)) {
            throw new IllegalPasswordFormatException();
        }
    }
}
