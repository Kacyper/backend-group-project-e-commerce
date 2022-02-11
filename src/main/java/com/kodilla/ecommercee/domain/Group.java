package com.kodilla.ecommercee.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "GROUPS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID_GROUP", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME_GROUP")
    private String groupName;

    @OneToMany(
            targetEntity = Product.class,
            mappedBy = "group",
            fetch = FetchType.EAGER
    )
    private List<Product> products;
}