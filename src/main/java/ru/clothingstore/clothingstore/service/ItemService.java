package ru.clothingstore.clothingstore.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clothingstore.clothingstore.model.CartItem;
import ru.clothingstore.clothingstore.model.Item;
import ru.clothingstore.clothingstore.repo.CartItemRepository;
import ru.clothingstore.clothingstore.repo.ItemImageRepository;
import ru.clothingstore.clothingstore.repo.ItemRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    private final CartItemRepository cartItemRepository;

    private final ItemImageRepository itemImageRepository;


    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public List<Item> getAllActiveItems(){
        return itemRepository.findAllByActiveTrue();
    }

    public List<Item> getAllItemsByCategory(String category, Long itemId){
        List<Item> allItemsByCategory = itemRepository.findAllByActiveTrueAndCategory(category);
        Item item = itemRepository.findItemById(itemId);
        allItemsByCategory.remove(item);

        return allItemsByCategory;
    }


    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public Item getItemById(Long id){
        return itemRepository.findItemById(id);
    }

    public void saveCartItem(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }

    public CartItem getCartItemById(Long id){
        return cartItemRepository.getById(id);
    }



}

