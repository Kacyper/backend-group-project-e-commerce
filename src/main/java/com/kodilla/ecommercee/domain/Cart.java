package com.kodilla.ecommercee.domain;

import javax.persistence.*;
import com.sun.istack.NotNull;
import jdk.jfr.Unsigned;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "CARTS")
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
  @Column(name = "ID_CART")
  private Long idCart;

  @NotNull
  @Column(name = "TOTAL")
  private BigDecimal total;

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
  @JoinColumn(name = "ORDER_ID")
  private Order order;
}