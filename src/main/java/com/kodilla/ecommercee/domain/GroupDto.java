package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.Validator.Request.GroupDtoRequestValid;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@GroupDtoRequestValid
public class GroupDto {
    private Long id;
    private String groupName;
}