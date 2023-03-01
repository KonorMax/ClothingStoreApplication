package ru.clothingstore.clothingstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clothingstore.clothingstore.model.ShoppingCart;


import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findAllByUserUsername(String username);

    List<ShoppingCart> findAllByUserUsernameAndCompleted(String username, Boolean isCompleted);

    List<ShoppingCart> findAllByUserUsernameAndCompletedOrderByDateDesc(String username, Boolean isCompleted);

    List<ShoppingCart> findAll();


    ShoppingCart findShoppingCartById(Long id);

    void deleteAllById(Long id);

}

