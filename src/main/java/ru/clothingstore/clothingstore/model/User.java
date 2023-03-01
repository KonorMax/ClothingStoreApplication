package ru.clothingstore.clothingstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String roles;

    @Column(nullable = false)
    private boolean isEnable;


    @Column(nullable = false, unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "user")
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String roles, String email, List<ShoppingCart> shoppingCarts) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.shoppingCarts = shoppingCarts;
    }

    public User(String username, String password, String roles, String email) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
    }


    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            System.out.println("User has roles");
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public Float getSumOfTotalOrders(){
        Float totalOrders = 0f;
        for (ShoppingCart shoppingCart : shoppingCarts){
            totalOrders += shoppingCart.calcCartPrice();
        }
        return totalOrders;
    }
}
