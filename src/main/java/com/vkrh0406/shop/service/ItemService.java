package com.vkrh0406.shop.service;

import com.vkrh0406.shop.Controller.search.ItemSearch;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.dto.ItemDto;
import com.vkrh0406.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;


    public Page<ItemDto> searchCategoryItems(ItemSearch itemSearch, Pageable pageable,Long categoryId) {

        //검색 조건이 있을때 체크
        if (itemSearch.getSearchType() != null) {
            itemSearch.checkSearchType();
        }

        Page<ItemDto> itemDtos = itemRepository.searchItemWithCategory(itemSearch, pageable, categoryId);
        return itemDtos;
    }

    public Page<ItemDto> searchAllItem(ItemSearch itemSearch,Pageable pageable){
        if (itemSearch.getSearchType() != null) {
            itemSearch.checkSearchType();
        }

        Page<ItemDto> itemDtos = itemRepository.searchItem(itemSearch, pageable);
        return itemDtos;
    }

    public List<Item> getHomeItem(){
        PageRequest pageRequest = PageRequest.of(0, 8);
        Pageable pageable = (Pageable) pageRequest;
        Page<Item> all = itemRepository.findAll(pageable);
        List<Item> result = all.getContent();

        return result;
    }


    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item findItemById(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("찾을 아이템이 없습니다"));
        return item;
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItem(Long itemId) {
        Item findItem = itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("삭제할 아이템이 없습니다"));
        itemRepository.delete(findItem);

    }


}
