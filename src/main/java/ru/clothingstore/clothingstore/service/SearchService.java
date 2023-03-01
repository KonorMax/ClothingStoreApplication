package ru.clothingstore.clothingstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clothingstore.clothingstore.model.Item;
import ru.clothingstore.clothingstore.repo.ItemRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ItemRepository itemRepository;

    public List<Item> getSearchedItems(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContaining(search, search, search, search);
    }

    public List<Item> getSearchedItemsOrderByName(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByName(search, search, search, search);
    }

    public List<Item> getSearchedItemsOrderByCategory(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByCategory(search, search, search, search);
    }

    public List<Item> getSearchedItemsOrderByBrand(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByBrand(search, search, search, search);
    }

    public List<Item> getSearchedItemsOrderByPriceAsc(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceAsc(search, search, search, search);
    }


    public List<Item> getSearchedItemsOrderByPriceDesc(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceDesc(search, search, search, search);
    }

    public List<Item> getSearchedItemsOrderByIdDesc(String search){
        return itemRepository
                .findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByIdDesc(search, search, search, search);
    }


    public List<Item> sortAllByIdDesc(){
        return itemRepository.findAllByActiveTrueOrderByIdDesc();
    }

    public List<Item> getItemsByName(String name){
        return itemRepository.findAllByNameContainingAndActiveTrue(name);
    }

    public List<Item> getItemsByCategory(String category){
        return itemRepository.findAllByCategoryAndActiveTrue(category);
    }

    public List<Item> sortAllByName(){
        return itemRepository.findAllByActiveTrueOrderByName();
    }

    public List<Item> sortAllByCategory(){
        return itemRepository.findAllByActiveTrueOrderByCategory();
    }

    public List<Item> sortAllByBrand(){
        return itemRepository.findAllByActiveTrueOrderByBrand();
    }

    public List<Item> sortAllByPriceAsc(){
        return itemRepository.findAllByActiveTrueOrderByPriceAsc();
    }

    public List<Item> sortAllByPriceDesc(){
        return itemRepository.findAllByActiveTrueOrderByPriceDesc();
    }

    public List<Item> sortAllById(){
        return itemRepository.findAllByActiveTrueOrderById();
    }

}
