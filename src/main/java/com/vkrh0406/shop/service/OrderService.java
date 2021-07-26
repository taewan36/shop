package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.*;
import com.vkrh0406.shop.dto.OrderDto;
import com.vkrh0406.shop.form.OrderForm;
import com.vkrh0406.shop.form.PayForm;
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



    //pay
    @Transactional
    public void payOrder(Long orderId, PayForm payForm) {
        Order findOrder = findOrderById(orderId);
        findOrder.getDelivery().setAddress(new Address(payForm.getAddrDetail(), payForm.getAddrPart1(), payForm.getAddrPart2(), payForm.getZipcode()));
        findOrder.setOrderStatus(OrderStatus.ORDER);
        findOrder.setOrderDate(LocalDateTime.now());

    }


    // 오더id로 오더 찾기
    public Order findOrderById(Long orderId) {
        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new IllegalStateException("이런 orderId는 존재하지 않습니다"));
        return order;
    }

    /// 오더 생성
    @Transactional
    public Long makeOrder(List<OrderForm> orderForms, Member member, HttpSession session) {


        //해당 멤버 찾기
        Member findMember = memberRepository.findMemberById(member.getId()).orElseThrow(() -> new IllegalStateException("이 멤버 id는 존재하지 않습니다"));
        //해당 멤버의 카트
        Cart cart = findMember.getCart();


        //오더 생성
        Order order = Order.CreateOrder(findMember, new Delivery(findMember.getAddress(), DeliveryStatus.READY), cart.getOrderItems());

        //오더 db에 저장
        orderRepository.save(order);

        //카트에서 orderItem들 꺼내기
        List<OrderItem> orderItems = cart.getOrderItems();

        //카트 지우고 다시 세션에 집어넣기
        orderItems.clear();
        long cartId = cart.getId();
        //orderRepository.deleteOrdersByCartId(cartId);

        cart.setSize(cart.getOrderItems().size());

        session.removeAttribute(SessionConst.SESSION_CART);
        session.setAttribute(SessionConst.SESSION_CART,cart);

        return order.getId();



    }

    // Order들을 OrderDto로 바꿔서 리턴
    public List<OrderDto> findAllOrder() {

        return orderRepository.findAll().stream()
                .map(o -> new OrderDto(o.getId(), o.getTotalPrice(), o.getOrderDate(), o.getOrderStatus(), o.getDelivery(), o.getOrderItems()))
                .collect(Collectors.toList());
    }
}
