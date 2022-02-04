package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
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
    @NotNull
    @Column(name = "CARTNAME")
    private String cartName;

    @NotNull
    @Column(name = "TOTAL")
    private BigDecimal total;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ORDER")
    private Order order;

}
