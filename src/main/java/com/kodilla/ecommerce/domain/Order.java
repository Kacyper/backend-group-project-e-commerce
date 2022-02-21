package com.kodilla.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NamedEntityGraph(name = "graph.Order.products", attributeNodes = @NamedAttributeNode("products"))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID_ORDER", unique = true)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Column(name = "ORDER_DATE")
    private LocalDate orderDate;

    @NotNull
    @Column(name = "SHIPPING_PRICE")
    private BigDecimal shippingPrice;

    @NotNull
    @Column(name = "PRODUCTS_TOTAL_PRICE")
    private BigDecimal productsTotalPrice;

    @NotNull
    @Column(name = "ORDER_TOTAL_PRICE")
    private BigDecimal orderTotalPrice;

    @NotNull
    @Column(name = "IS_SENT")
    private boolean sent;

    @NotNull
    @Column(name = "IS_PAID")
    private boolean paid;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER")
    private User user;

    @ManyToMany(
            targetEntity = Product.class,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            },
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "ORDERS_HAVE_PRODUCTS",
            joinColumns = {@JoinColumn(name = "ID_ORDER", referencedColumnName = "ID_ORDER")},
            inverseJoinColumns = {@JoinColumn(name = "ID_PRODUCT", referencedColumnName = "ID_PRODUCT")}
    )
    private List<Product> products;
}