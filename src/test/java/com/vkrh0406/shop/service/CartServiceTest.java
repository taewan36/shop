package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.dto.CartDto;
import com.vkrh0406.shop.dto.OrderItemDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;

    Logger log = LoggerFactory.getLogger("CartServiceTest");

    @Test
    void saveOrderItemToCart() {

        //given
        List<Item> all = itemService.findAll();
        Item item = all.get(0);

        Cart cart = new Cart();
        Long cartId = cartService.saveCart(cart);


        //when
        cartService.saveOrderItemToCart(cart, item.getId());


        //then
        Cart byId = cartService.findById(cartId);

        assertThat(cartService.findAll().size()).isEqualTo(1);
        assertThat(byId.getOrderItems().size()).isEqualTo(1);
    }

    @Test
    void makeCartDto(){
        //given
        List<Item> all = itemService.findAll();
        Item item = all.get(0);

        Cart cart = new Cart();
        Long cartId = cartService.saveCart(cart);


        //when
        cartService.saveOrderItemToCart(cart, item.getId());

        CartDto cartDto = cartService.makeCartDto(cart);

        List<OrderItemDto> orderItems = cartDto.getOrderItems();

//        for (OrderItemDto orderItem : orderItems) {
//            log.info("오더아이템 {}", orderItem.getItemName());
//        }

        //then
        assertThat(orderItems.size()).isEqualTo(1);

    }
}