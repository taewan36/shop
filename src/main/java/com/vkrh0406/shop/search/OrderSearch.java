package com.vkrh0406.shop.search;

import com.vkrh0406.shop.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
