package com.vkrh0406.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {

    private Long id;

    private List<OrderItemDto> orderItems;

    public CartDto(Long id, List<OrderItemDto> orderItems) {
        this.id = id;
        this.orderItems = orderItems;
    }
}
