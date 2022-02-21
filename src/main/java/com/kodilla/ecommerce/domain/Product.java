package com.kodilla.ecommerce.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NamedQuery(
        name = "Product.retrieveAvailableProducts",
        query = "FROM PRODUCTS WHERE available = true")

@Entity(name = "PRODUCTS")
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

    @NotNull
    @Column(name = "PRICE")
    private BigDecimal price;

    @NotNull
    @Column(name = "PRODUCT_DESCRIPTION")
    private String productDescription;

    @NotNull
    @Column(name = "IS_AVAILABLE")
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "ID_GROUP")
    public Group group;
}
