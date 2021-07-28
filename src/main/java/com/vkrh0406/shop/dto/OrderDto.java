package com.vkrh0406.shop.dto;

import com.vkrh0406.shop.domain.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    private Long orderId;

    private int totalPrice;
    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

    private Delivery delivery;

    private List<Item> items;

    private List<OrderItem> orderItems;

    public OrderDto(Long orderId, int totalPrice, LocalDateTime orderDate, OrderStatus orderStatus, Delivery delivery, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.delivery = delivery;
        this.orderItems = orderItems;
    }
}
