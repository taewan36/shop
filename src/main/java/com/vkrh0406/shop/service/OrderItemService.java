package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.OrderItem;
import com.vkrh0406.shop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItem findById(Long id){
        return orderItemRepository.findOrderItemById(id).orElseThrow(() -> new IllegalStateException("id로 OrderItem 을 찾을수 없습니다."));
    }


}
