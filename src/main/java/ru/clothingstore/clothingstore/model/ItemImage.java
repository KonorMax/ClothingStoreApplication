package ru.clothingstore.clothingstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Base64;

@Data
@Entity
@Table(name = "item_image")
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Item item;


    private byte[] imgPath;

    public ItemImage() {
    }

    public ItemImage(Item item, byte[] imgPath) {
        this.item = item;
        this.imgPath = imgPath;
    }


    public String getDecodedImgPath(){
        return new String(Base64.getDecoder().decode(imgPath));
    }
}