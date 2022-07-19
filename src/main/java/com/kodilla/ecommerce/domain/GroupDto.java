package com.kodilla.ecommerce.domain;

import lombok.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    private Long id;
    private String groupName;
    private List<Long> productIds;
}
