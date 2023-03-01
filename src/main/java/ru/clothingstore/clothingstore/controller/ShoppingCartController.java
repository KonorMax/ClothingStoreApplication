package ru.clothingstore.clothingstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.clothingstore.clothingstore.model.CartItem;
import ru.clothingstore.clothingstore.model.Item;
import ru.clothingstore.clothingstore.model.ShoppingCart;
import ru.clothingstore.clothingstore.model.User;
import ru.clothingstore.clothingstore.service.ItemService;
import ru.clothingstore.clothingstore.service.ShoppingCartService;
import ru.clothingstore.clothingstore.service.UserService;


import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("shoppingCart")
    public String shoppingCart(Principal principal, Model model){
        User user = userService.getUser(principal.getName());
        ShoppingCart searchedCart = shoppingCartService
                .getActiveShoppingCartByUsername(principal.getName(), false);
        if(searchedCart == null){
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUser(user);
            shoppingCartService.saveShoppingCart(newCart);
            user.setShoppingCart(newCart);
            userService.saveUser(user);
            model.addAttribute("cart",newCart);
        } else {
            model.addAttribute("cart", searchedCart);
        }

        return "shoppingCart";


    }


    @GetMapping("item/{id}")
    public String viewItem(@PathVariable(value = "id") Long id, Model model){
        Item item = itemService.getItemById(id);
        List<Item> items = itemService.getAllItemsByCategory(item.getCategory(), id);
        model.addAttribute("item", item);
        model.addAttribute("items", items);
        System.out.println("Total amount without searched item: " + items.size());
        return "item/item";
    }

    @GetMapping("item/addToCart/{id}")
    public String addToCart(@PathVariable(value = "id") Long id, Principal principal){
        if(principal == null){
            return "login";
        }

        ShoppingCart shoppingCart = shoppingCartService.getActiveShoppingCartByUsername(principal.getName(), false);
        Item itemForAdd = itemService.getItemById(id);

        if (shoppingCart.isItemInCart(itemForAdd)){
            CartItem cartItem = shoppingCart.getCartItemByItem(itemForAdd);
            int cartItemQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(cartItemQuantity);
            itemService.saveCartItem(cartItem);

        } else {
            CartItem newCartItem = new CartItem(itemForAdd, 1);
            newCartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(newCartItem);
            itemService.saveCartItem(newCartItem);
            shoppingCartService.saveShoppingCart(shoppingCart);
        }

        return "redirect:/";
    }

    @GetMapping("addQuantity/{id}")
    public String addQuantity(@PathVariable(value = "id")Long id){
        CartItem cartItem = itemService.getCartItemById(id);
        int previousQuantity = cartItem.getQuantity();
        cartItem.setQuantity(previousQuantity + 1);
        itemService.saveCartItem(cartItem);
        shoppingCartService.saveShoppingCart(cartItem.getShoppingCart());

        return "redirect:/shoppingCart";
    }

    @GetMapping("decreaseQuantity/{id}")
    public String decreaseQuantity(@PathVariable(value = "id")Long id, Principal principal){
        CartItem cartItem = itemService.getCartItemById(id);
        int previousQuantity = cartItem.getQuantity();
        cartItem.setQuantity(previousQuantity - 1);


        if(previousQuantity <= 1){
            ShoppingCart shoppingCart = shoppingCartService
                    .getActiveShoppingCartByUsername(principal.getName(), false);
            shoppingCart.getCartItems().remove(cartItem);
            shoppingCartService.saveShoppingCart(shoppingCart);
        }

        itemService.saveCartItem(cartItem);
        shoppingCartService.saveShoppingCart(cartItem.getShoppingCart());

        return "redirect:/shoppingCart";
    }

    @GetMapping("order/{id}")
    public String checkOut(@PathVariable(value = "id")Long id,
                           Principal principal,
                           RedirectAttributes attributes){
        User user = userService.getUser(principal.getName());

        ShoppingCart shoppingCart = shoppingCartService.getShoppingCartById(id);
        shoppingCart.setCompleted(true);
        shoppingCart.setPrice(shoppingCart.calcCartPrice());
        shoppingCart.setDate(new Date());
        shoppingCartService.saveShoppingCart(shoppingCart);

        user.getShoppingCarts().add(shoppingCart);
        ShoppingCart newShoppingCart = new ShoppingCart();
        newShoppingCart.setUser(user);
        user.setShoppingCart(newShoppingCart);
        userService.saveUser(user);
        attributes.addFlashAttribute("checkoutSuccess", shoppingCart.calcCartPrice());
        attributes.addFlashAttribute("checkoutSuccess2", shoppingCart.calcNumberOfItems());

        return "redirect:/";
    }




}