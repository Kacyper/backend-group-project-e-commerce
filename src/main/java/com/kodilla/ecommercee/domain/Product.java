package com.kodilla.ecommercee.domain;



import jdk.jfr.Unsigned;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity(name = "product")
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
    @Column(name = "QUANTITY")
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GROUP")
    private Group group;

}