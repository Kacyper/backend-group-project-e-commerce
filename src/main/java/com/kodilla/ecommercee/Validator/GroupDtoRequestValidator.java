package com.kodilla.ecommercee.Validator;

import com.kodilla.ecommercee.Validator.Request.GroupDtoRequestValid;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.exception.groupException.InvalidGroupDtoRequestException;
import lombok.SneakyThrows;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static java.util.Optional.ofNullable;

public class GroupDtoRequestValidator implements ConstraintValidator<GroupDtoRequestValid, GroupDto> {

    @SneakyThrows
    @Override
    public boolean isValid(GroupDto request, ConstraintValidatorContext constraintValidatorContext) {

        if (ofNullable(request.getId()).isPresent() && ofNullable(request.getGroupName()).isPresent()) {
            return true;

        } else throw new InvalidGroupDtoRequestException();
    }
}
