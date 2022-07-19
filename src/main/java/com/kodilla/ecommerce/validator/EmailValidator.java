package com.kodilla.ecommerce.validator;

import com.kodilla.ecommerce.exception.EmailNotValidException;
import org.springframework.stereotype.Service;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(final String email) {

        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }
    public void validate(final String email) throws EmailNotValidException {

        if (!test(email)) {
            throw new EmailNotValidException();
        }
    }
}
