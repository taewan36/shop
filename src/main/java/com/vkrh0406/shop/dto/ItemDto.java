package com.vkrh0406.shop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ItemDto {

    private Long id;
    private String name;
    private int price;
    private int discountPrice;

    private int ratingStar;

    private String imageSource;

    public ItemDto(Long id, String name, int price, int discountPrice, int ratingStar, String imageSource) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.ratingStar = ratingStar;
        this.imageSource = imageSource;
    }
}
