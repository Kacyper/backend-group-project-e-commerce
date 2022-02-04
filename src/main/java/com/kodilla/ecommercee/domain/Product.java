package com.kodilla.ecommercee.domain;

import jdk.jfr.Unsigned;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    @Unsigned
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