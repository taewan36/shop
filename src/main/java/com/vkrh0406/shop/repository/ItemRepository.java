package com.vkrh0406.shop.repository;

import com.vkrh0406.shop.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Long> {
    public Optional<Item> findById(Long id);

}
