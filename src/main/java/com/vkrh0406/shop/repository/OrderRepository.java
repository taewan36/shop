package com.vkrh0406.shop.repository;

import com.vkrh0406.shop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public Optional<Order> findById(Long id);
}
