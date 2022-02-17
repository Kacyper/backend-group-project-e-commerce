package com.kodilla.ecommercee.validator;

import org.springframework.stereotype.Service;
import java.util.function.BiPredicate;

@Service
public class PasswordEqualityValidator implements BiPredicate<String, String> {
    @Override
    public boolean test(final String password,final String repeatedPassword) {
        return password.equals(repeatedPassword);
    }
}
