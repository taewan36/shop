package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.domain.OrderItem;
import com.vkrh0406.shop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ItemService itemService;

    public Cart findById(Long id) {
        Optional<Cart> cartById = cartRepository.findCartById(id);
        return cartById.orElseThrow(() -> new IllegalStateException("이 id로 카트를 찾을수가 없습니다."));
    }

    public List<Cart> findAll(){
        return cartRepository.findAll();
    }

    public Long saveCart(Cart cart) {
        cartRepository.save(cart);
        return cart.getId();
    }

    @Transactional
    public Cart saveOrderItemToCart(Long cartId,Long itemId) {

        boolean findItemBoolean=false;

        Item findItem = itemService.findItemById(itemId);

        Cart findCart = cartRepository.findCartById(cartId).orElseThrow(() -> new IllegalStateException("이 id의 카트가 없습니다."));
        List<OrderItem> orderItems = findCart.getOrderItems();

        //기존 오더아이템에 같은 아이템이 있을경우 카운트만 증가
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getItem().getId().equals(itemId)) {
                orderItem.addCount(1);
                findItemBoolean=true;
            }
        }
        //기존 오더아이템에 같은아이템이 없을경우 새로 추가
        if(findItemBoolean==false){
            OrderItem orderItem = new OrderItem(findItem,1);
            orderItem.getOrderPrice();
            findCart.addOrderItem(orderItem);
        }


        return findCart;


    }



}
