package ru.clothingstore.clothingstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.clothingstore.clothingstore.model.ShoppingCart;
import ru.clothingstore.clothingstore.service.ShoppingCartService;


import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final ShoppingCartService shoppingCartService;


    @GetMapping("user/orderHistory")
    public String cartHistory(Principal principal, Model model){
        List<ShoppingCart> shoppingCarts = shoppingCartService
                .getCompletedShoppingCarts(principal.getName(), true);
        if(shoppingCarts == null){
            model.addAttribute("noOrdersYet", "User hasn't any orders");
        }
        model.addAttribute("carts", shoppingCarts);
        return "user/orderHistory";
    }

    @GetMapping("user/viewOrdered/{id}")
    public String viewOrder(@PathVariable("id") Long id, Model model){
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartById(id);
        model.addAttribute("order", shoppingCart);
        return "user/orderHistory";
    }
}
