package ru.clothingstore.clothingstore.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clothingstore.clothingstore.model.ItemImage;
import ru.clothingstore.clothingstore.repo.ItemImageRepository;


@Service
@RequiredArgsConstructor
public class ItemImageService {
    private final ItemImageRepository itemImageRepository;

    public void saveItemImage(ItemImage itemImage){
        itemImageRepository.save(itemImage);
    }

    public void deleteItemImage(Long id){
        itemImageRepository.deleteAllById(id);
    }
}

