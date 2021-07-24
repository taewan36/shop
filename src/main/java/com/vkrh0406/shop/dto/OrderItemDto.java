package com.vkrh0406.shop.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long id;

    private Long itemId;
    private String itemName;
    private String itemImageSource;

    private int count;
    private int price;

    public OrderItemDto(Long id, Long itemId, String itemName, String itemImageSource, int count, int price) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImageSource = itemImageSource;
        this.count = count;
        this.price = price;
    }
}
