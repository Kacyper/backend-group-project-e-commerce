package com.kodilla.ecommercee.domain;



import lombok.*;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.List;

@Entity(name = "CARTS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "ID_CART", unique = true)
    private Long id;
    @NotNull
    @Column(name = "NAME_CART")
    private String cartName;

    @ManyToMany(
        targetEntity = Product.class,
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "CARTS_HAVE_PRODUCTS",
        joinColumns = {@JoinColumn(name = "ID_CART", referencedColumnName = "ID_CART")},
        inverseJoinColumns = {@JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID_PRODUCT")}
    )
    private List<Product> products;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ORDER")
    private Order order;
}
