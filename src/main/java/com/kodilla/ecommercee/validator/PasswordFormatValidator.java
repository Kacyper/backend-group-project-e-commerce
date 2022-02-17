package com.kodilla.ecommercee.validator;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;

@Service
public class PasswordFormatValidator implements Predicate<String> {
    @Override
    public boolean test(final String password) {
        return password != null && password.length() > 5 && !password.isBlank();
    }
}
