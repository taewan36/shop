package com.vkrh0406.shop.repository;

import com.vkrh0406.shop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    public Optional<Category> findCategoryByName(String name);


}
