package com.kodilla.ecommercee.domain;

import lombok.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    private Long id;
    private String groupName;
    private List<Product> products;
}
