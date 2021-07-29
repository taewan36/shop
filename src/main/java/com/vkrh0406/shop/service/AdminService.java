package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.*;
import com.vkrh0406.shop.dto.OrderDto;
import com.vkrh0406.shop.repository.MemberRepository;
import com.vkrh0406.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Page<Member> getMemberList(Member member, Pageable pageable) {
        //어드민체크
        adminCheck(member);
        Page<Member> all = memberRepository.findAll(pageable);

        return all;
    }

    public void deleteMember(Member member, Long memberId) {
        //어드민체크
        adminCheck(member);

        //본인 아이디를 삭제하려는 경우
        if (member.getId().equals(memberId)) {
            throw new IllegalStateException("자신의 아이디는 삭제할 수 없습니다.");
        }
        //멤버 조회
        Member findMember = memberRepository.findMemberById(memberId).orElseThrow(() -> new IllegalStateException("이 memberId가 없습니다"));

        //이 멤버가 어드민이면 삭제 불가능
        if (findMember.isAdmin()) {
            throw new IllegalStateException("어드민 아이디는 삭제할 수 없습니다.");
        }

        //멤버 삭제
        memberRepository.delete(findMember);


    }

    private void adminCheck(Member member) {
        //어드민이 아닐경우
        if (!member.isAdmin()) {
            throw new IllegalStateException("어드민이 아닙니다");
        }
    }

    // Page<OrderDto> 를 리턴함
    public Page<OrderDto> getOrderDtoList(Member member,Pageable pageable) {
        //어드민 체크
        adminCheck(member);

        Page<Order> orders = orderRepository.findAll(pageable);

        return orders.map(OrderDto::of);

    }

    // Page<OrderDto> 를 리턴함
    public Page<OrderDto> getOrderedOrderDtoList(Member member,Pageable pageable) {
        //어드민 체크
        adminCheck(member);

        Page<Order> orders = orderRepository.findOrdersByOrderStatus(OrderStatus.ORDER,pageable);

        return orders.map(OrderDto::of);

    }


    @Transactional
    public void updateOrderStatus(Member member, Long orderId, OrderStatus orderStatus) {
        //어드민 체크
        adminCheck(member);

        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new IllegalStateException("이런 orderId 는 없습니다."));
        order.setOrderStatus(orderStatus);

    }

    @Transactional
    public void updateDeliveryStatus(Member member, Long orderId, DeliveryStatus deliveryStatus) {
        //어드민 체크
        adminCheck(member);

        Order order = orderRepository.findOrderById(orderId).orElseThrow(() -> new IllegalStateException("이런 orderId 는 없습니다."));
        Delivery delivery = order.getDelivery();
        delivery.setStatus(deliveryStatus);

    }

    @Transactional
    public void deleteOrder(Member member, Long orderId) {
        //어드민 체크
        adminCheck(member);

        orderRepository.deleteById(orderId);



    }
}
