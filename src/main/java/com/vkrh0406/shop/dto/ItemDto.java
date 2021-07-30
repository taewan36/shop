package com.vkrh0406.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ItemDto {

    private Long id;
    private String name;
    private int price;
    private int discountPrice;

    private int quantity;

    private int ratingStar;

    private Long categoryId;
    private String categoryName;

    private String imageSource;

//    @QueryProjection
//    public ItemDto(Long id, String name, int price, int discountPrice, int ratingStar, String imageSource) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.discountPrice = discountPrice;
//        this.ratingStar = ratingStar;
//        this.imageSource = imageSource;
//    }

    @QueryProjection
    public ItemDto(Long id, String name, int price, int discountPrice, int ratingStar, Long categoryId, String categoryName, String imageSource,int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.ratingStar = ratingStar;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.imageSource = imageSource;
        this.quantity = quantity;
    }
}
