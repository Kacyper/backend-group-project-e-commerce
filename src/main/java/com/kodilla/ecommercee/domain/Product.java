package com.kodilla.ecommercee.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;


@Entity(name = "products")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Product {

    @Id
    @GeneratedValue
    @NotNull

    @Column(name = "ID_PRODUCT", unique = true)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_GROUP")
    private Group group;

    @ManyToMany(cascade = CascadeType.ALL,
            mappedBy = "products"
    )
    private List<Cart> carts;
}