package com.vkrh0406.shop.repository;

import com.vkrh0406.shop.Controller.search.ItemSearch;
import com.vkrh0406.shop.dto.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom{
    public Page<ItemDto> searchItem(ItemSearch itemSearch, Pageable pageable);

    public Page<ItemDto> searchItemWithCategory(ItemSearch itemSearch, Pageable pageable,Long categoryId);

}
