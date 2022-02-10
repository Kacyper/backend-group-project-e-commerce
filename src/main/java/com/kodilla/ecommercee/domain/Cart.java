package com.kodilla.ecommercee.domain;



import lombok.*;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @NotNull
    @Column(name = "NAME_CART")
    private String cartName;

    @ManyToMany(
            targetEntity = Product.class,
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                   // CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH}
    )
    @JoinTable(
            name = "CARTS_HAVE_PRODUCTS",
            joinColumns = {@JoinColumn(name = "ID_CART", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "ID_PRODUCT", nullable = false,
                    updatable = false)}
    )
    private List<Product> products;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ORDER")
    private Order order;
}