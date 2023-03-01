package ru.clothingstore.clothingstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clothingstore.clothingstore.model.Item;


import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemById(Long id);

    List<Item> findAll();

    List<Item> findAllByActiveTrue();

    List<Item> findAllByNameContainingAndActiveTrue(String name);

    List<Item> findAllByCategoryAndActiveTrue(String name);
    List<Item> findAllByActiveTrueAndCategory(String category);

    List<Item> findAllByActiveTrueOrderById();

    List<Item> findAllByActiveTrueOrderByName();

    List<Item> findAllByActiveTrueOrderByCategory();


    List<Item> findAllByActiveTrueOrderByBrand();

    List<Item> findAllByActiveTrueOrderByIdDesc();

    List<Item> findAllByActiveTrueOrderByPriceAsc();

    List<Item> findAllByActiveTrueOrderByPriceDesc();


    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContaining
            (String name, String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByName
            (String name, String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByCategory
            (String name, String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByBrand
            (String name, String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceAsc
            (String name, String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceDesc
            (String name, String category, String description, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByIdDesc
            (String name, String category, String description, String brand);



}

