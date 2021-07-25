package com.vkrh0406.shop.repository;

import com.vkrh0406.shop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public Optional<Order> findById(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update OrderItem o set o.cart.id = NULL where o.cart.id = :cartId")
    public void deleteOrdersByCartId(@Param("cartId")long cartId);

}
