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

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "PRICE")
    private BigDecimal price;

    @NotNull
    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "ID_GROUP")
    public Group group;

    @ManyToMany(cascade = CascadeType.ALL,
    mappedBy = "products"
    )
    private List<Cart> carts;
}
