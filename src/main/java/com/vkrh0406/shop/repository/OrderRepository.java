package com.vkrh0406.shop.repository;

import com.vkrh0406.shop.domain.Order;
import com.vkrh0406.shop.domain.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public Optional<Order> findOrderById(Long id);

    public Optional<Order> findOrderByUuid(String uuid);


    @Modifying(clearAutomatically = true)
    @Query(value = "update OrderItem o set o.cart.id = NULL where o.cart.id = :cartId")
    public void deleteOrdersByCartId(@Param("cartId")long cartId);

    public List<Order> findOrdersByMemberId(Long memberId);

    public List<Order> findOrdersByMemberIdOrderByIdDesc(Long memberId);

    public Page<Order> findOrdersByOrderStatus(OrderStatus orderStatus, Pageable pageable);

}
