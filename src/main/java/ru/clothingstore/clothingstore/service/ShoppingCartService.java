package ru.clothingstore.clothingstore.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clothingstore.clothingstore.model.ShoppingCart;
import ru.clothingstore.clothingstore.repo.ShoppingCartRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;


    public ShoppingCart getShoppingCartById(Long id){
        return shoppingCartRepository.findShoppingCartById(id);
    }

    public ShoppingCart getActiveShoppingCartByUsername(String username, Boolean isCompleted){
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUserUsernameAndCompleted(username, isCompleted);

        if(!shoppingCarts.isEmpty()){
            return shoppingCarts.get(0);
        }

        return new ShoppingCart();
    }

    public List<ShoppingCart> getCompletedShoppingCarts(String username, Boolean isCompleted){
        return shoppingCartRepository.findAllByUserUsernameAndCompletedOrderByDateDesc(username, isCompleted);
    }


    public List<ShoppingCart> getAllShoppingCarts(){
        return shoppingCartRepository.findAll();
    }

    public void saveShoppingCart(ShoppingCart shoppingCart){
        shoppingCartRepository.save(shoppingCart);
    }

    public void deleteShoppingById(Long id){
        shoppingCartRepository.deleteById(id);
    }
}
