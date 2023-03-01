package ru.clothingstore.clothingstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.clothingstore.clothingstore.model.ItemImage;


public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    void deleteAllById(Long id);

}