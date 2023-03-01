package ru.clothingstore.clothingstore.model;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    private Item item;

    private int quantity;

    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
