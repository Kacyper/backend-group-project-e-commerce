package com.kodilla.ecommerce.validator;

import com.kodilla.ecommerce.exception.PasswordNotMatchException;
import org.springframework.stereotype.Service;
import java.util.function.BiPredicate;

@Service
public class PasswordEqualityValidator implements BiPredicate<String, String> {
    @Override
    public boolean test(final String password,final String repeatedPassword) {
        return password.equals(repeatedPassword);
    }
    public void validate(final String password, final String repeatedPassword)
            throws PasswordNotMatchException {
        if (!test(password, repeatedPassword)) {
            throw new PasswordNotMatchException();
        }
    }
}
