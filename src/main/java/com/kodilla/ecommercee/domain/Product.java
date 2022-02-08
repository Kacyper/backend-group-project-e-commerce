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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID_PRODUCT", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "ID_GROUP")
    public Group group;

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Product> products;

    @ManyToMany(cascade = CascadeType.ALL,
            mappedBy = "products"
    )
    private List<Cart> carts;
}
