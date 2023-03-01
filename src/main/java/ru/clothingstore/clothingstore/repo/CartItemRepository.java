package ru.clothingstore.clothingstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clothingstore.clothingstore.model.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem getById(Long id);

}