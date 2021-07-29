package com.vkrh0406.shop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @GeneratedValue(generator = "uuid2")
    private String uuid;

    private int totalPrice;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    //생성 메서드
    public static Order CreateOrder(Member member, Delivery delivery, List<OrderItem> orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setUuid(UUID.randomUUID().toString());

        int totalPrice=0;
        for (OrderItem orderItem : orderItems) {
            //오더 생성을 위해 아이템 잔고 확인하고 잔고 숫자 업데이트
            orderItem.removeItemCount();

            order.addOrderItem(orderItem);
            //아직 save 되기전 order를 담아도 되는가??? 잘 모르겠음

            orderItem.setOrder(order);
            totalPrice += orderItem.getOrderPrice();
        }

        order.setOrderStatus(OrderStatus.PRE_ORDER);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(totalPrice);

        return order;
    }

    //주문 취소
    public void cancel(){

        if (orderStatus == OrderStatus.ORDER_COMPLETE) {
            throw new IllegalStateException("주문 완료 상태입니다.");
        }

        if (delivery.getStatus() == DeliveryStatus.DELIVERING) {
            throw new IllegalStateException("이미 배송출발한 상품은 취소가 불가능합니다.");
        }
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setOrderStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }







}
