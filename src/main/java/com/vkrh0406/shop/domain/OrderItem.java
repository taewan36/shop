package com.vkrh0406.shop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "orderItem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int orderPrice;

    private int count;




    public OrderItem(Item item, int count) {
        this.item = item;
        this.count = count;
        this.orderPrice = item.getPrice() * count;
    }

    public void addCount(int count){
        item.removeQuantity(count);
        this.count+=count;
        getOrderPrice();
    }
    public void removeCount(int count) {
        int restCount;
        restCount = this.count - count;
        if (restCount < 0) {
            throw new IllegalStateException("제거하려는 카운트가 더 큽니다");
        }
        this.count = restCount;
        item.addStock(count);

    }

    public void setCount(int count) {
        this.count = count;
    }

    //주문한 아이템의 총 가격
    public int getOrderPrice() {
        this.orderPrice = item.getPrice() * count;
        return this.orderPrice;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void cancel() {
        getItem().addStock(count);
    }
}
