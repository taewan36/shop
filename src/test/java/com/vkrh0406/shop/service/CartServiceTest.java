package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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

    @Test
    void saveOrderItemToCart() {

        //given
        List<Item> all = itemService.findAll();
        Item item = all.get(0);

        Cart cart = new Cart();
        Long cartId = cartService.saveCart(cart);


        //when
        cartService.saveOrderItemToCart(cartId, item.getId());


        //then
        Cart byId = cartService.findById(cartId);

        assertThat(cartService.findAll().size()).isEqualTo(1);
        assertThat(byId.getOrderItems().size()).isEqualTo(1);
    }
}