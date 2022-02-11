package com.kodilla.ecommercee.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "CARTS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID_CART", unique = true)
    private Long id;

    @ManyToMany(
        targetEntity = Product.class,
        cascade = {
                CascadeType.PERSIST,
                CascadeType.DETACH,
                CascadeType.MERGE,
                CascadeType.REFRESH
        },
        fetch = FetchType.LAZY

    )
    @JoinTable(
            name = "CARTS_HAVE_PRODUCTS",
            joinColumns = {@JoinColumn(name = "ID_CART", referencedColumnName = "ID_CART")},
            inverseJoinColumns = {@JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID_PRODUCT")}
    )
    private List<Product> products;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER")
    private User user;
}
