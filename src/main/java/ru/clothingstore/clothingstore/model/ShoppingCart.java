package ru.clothingstore.clothingstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private float price;

    private Date date;

    private boolean completed = false;

    @ManyToOne
    private User user;


    @OneToMany
    private List<CartItem> cartItems = new ArrayList<>();

    public ShoppingCart() {
    }

    public ShoppingCart(float price, User user, List<CartItem> cartItems) {
        this.price = price;
        this.user = user;
        this.cartItems = cartItems;
    }


    public Float calcCartPrice() {
        float price = 0;
        if(cartItems.isEmpty()) {
            return price;
        }

        for(CartItem cartItem: cartItems){
            price += (cartItem.getItem().getPrice() * cartItem.getQuantity());
        }
        return price;
    }


    public boolean isItemInCart(Item item){
        for(CartItem cartItem: cartItems){
            return cartItem.getItem().getId() == item.getId();
        }
        return false;
    }

    public CartItem getCartItemByItem(Item item) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getItem() == item) {
                return cartItem;
            }
        }
        return null;
    }

    public int calcNumberOfItems(){
        int quantity = 0;

        if(cartItems.isEmpty()){
            return quantity;
        }

        for (CartItem cartItem: cartItems){
            quantity += cartItem.getQuantity();
        }
        return quantity;
    }


}

