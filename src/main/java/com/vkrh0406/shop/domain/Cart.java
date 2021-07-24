package com.vkrh0406.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Cart {

    @Id @GeneratedValue
    @Column(name = "cart_id")
    private Long id;

    private int size;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setCart(this);
        size++;
    }

    public void deleteOrderItem(Long orderItemId) {
        orderItems.removeIf(o -> o.getId() == orderItemId);
    }


    public Cart() {
    }
}
