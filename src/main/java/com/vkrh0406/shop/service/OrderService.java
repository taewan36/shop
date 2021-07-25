package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.*;
import com.vkrh0406.shop.dto.OrderDto;
import com.vkrh0406.shop.form.OrderForm;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.repository.MemberRepository;
import com.vkrh0406.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void makeOrder(List<OrderForm> orderForms, Member member, HttpSession session) {


        Member findMember = memberRepository.findMemberById(member.getId()).orElseThrow(() -> new IllegalStateException("이 멤버 id는 존재하지 않습니다"));
        Cart cart = findMember.getCart();



        Order order = Order.CreateOrder(findMember, new Delivery(findMember.getAddress(), DeliveryStatus.READY), cart.getOrderItems());

        orderRepository.save(order);

        List<OrderItem> orderItems = cart.getOrderItems();

        //카트 지우고 다시 세션에 집어넣기
        orderItems.clear();
        long cartId = cart.getId();
        orderRepository.deleteOrdersByCartId(cartId);

        cart.setSize(cart.getOrderItems().size());

        session.removeAttribute(SessionConst.SESSION_CART);
        session.setAttribute(SessionConst.SESSION_CART,cart);




    }

    public List<OrderDto> findAllOrder() {

        return orderRepository.findAll().stream()
                .map(o -> new OrderDto(o.getId(), o.getTotalPrice(), o.getOrderDate(), o.getOrderStatus(), o.getDelivery(), o.getOrderItems()))
                .collect(Collectors.toList());
    }
}
