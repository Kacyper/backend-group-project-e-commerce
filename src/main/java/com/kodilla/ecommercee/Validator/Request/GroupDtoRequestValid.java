package com.kodilla.ecommercee.Validator.Request;

import com.kodilla.ecommercee.Validator.GroupDtoRequestValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = GroupDtoRequestValidator.class)
@Documented
public @interface GroupDtoRequestValid {

    String message() default "Invalid GroupDto request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
