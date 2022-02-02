package com.kodilla.ecommercee.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    private Long id;
    private String groupName;
    private double price;
    private String description;
}
