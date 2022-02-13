package com.kodilla.ecommercee.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@NamedQuery(
        name = "Product.retrieveAvailableProducts",
        query = "FROM PRODUCTS WHERE isAvailable = true")
@Entity(name = "PRODUCTS")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotNull
    @Column(name = "IS_AVAILABLE")
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "ID_GROUP")
    public Group group;
}
