package ru.clothingstore.clothingstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private float price;

    @Column(length = 1000)
    private String description;

    private String category;

    private String brand;

    private boolean active;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item")
    private List<ItemImage> images = new java.util.ArrayList<>();

    public Item(String name, float price, String description, String category, String brand) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.brand = brand;
    }

    public Item() {
    }

    public Item(String name, float price, String description, String category, String brand, List<ItemImage> images) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.images = images;
    }


    public List<String> imagesPaths(){
        List<String> imagePaths = new ArrayList<>();

        for(ItemImage itemImage: images){
            imagePaths.add(new String(Base64.getDecoder().decode(itemImage.getImgPath())));
        }
        return imagePaths;
    }
}

