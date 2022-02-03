package com.kodilla.ecommercee.domain;

import jdk.jfr.Unsigned;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "carts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Cart {


    @Id
    @GeneratedValue
    @NotNull
    @Unsigned
    @NotNull
    @Column(name = "ID_CART")
    private Long idCart;

    @ManyToMany(
            targetEntity = Product.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinTable(name = "CARTS_HAVE_PRODUCTS",
            joinColumns = {@JoinColumn(name = "ID_CART", referencedColumnName = "ID_CART")},
            inverseJoinColumns = {@JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID_PRODUCT")}
    )
    private List<Product> products;

}
