package com.kodilla.ecommercee.domain;

import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity(name = "GROUPSX")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Unsigned
    @Column(name = "ID_GROUP", unique = true)
    private Long groupId;

    @NotNull
    @Column(name = "NAME")
    private String groupName;

    @NotNull
    @Column(name = "PRICE")
    private double price;

    @Column(name = "product_description")
    private String productDescription;


    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Product> products;
}
