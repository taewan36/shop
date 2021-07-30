package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.Category;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    Logger log = LoggerFactory.getLogger("ItemServiceTest");

//    @BeforeEach
//    private void createCategoryEntities() {
//
//        log.info("카테고리 엔티티 생성");
//
//        Category sub1 = new Category("SUB1"   ,1L, 0L);
//        Category sub2 = new Category("SUB2"   ,2L, 0L);
//        Category sub11 = new Category("SUB1-1",3L, 1L);
//        Category sub12 = new Category("SUB1-2",4L, 1L);
//        Category sub21 = new Category("SUB2-1",5L, 2L);
//        Category sub22 = new Category("SUB2-2",6L, 2L);
//
//        categoryRepository.save(sub1);
//        categoryRepository.save(sub2);
//        categoryRepository.save(sub11);
//        categoryRepository.save(sub12);
//        categoryRepository.save(sub21);
//        categoryRepository.save(sub22);
//
//    }
//
//
//    @Test
//    @Rollback
//    void getHomeItem() {
//
//        //given
//
//        Item item = new Item(1000, 900, 55, 5, null, categoryService.findCategoryByName("SUB1"));
//        itemService.saveItem(item);
//
//        Item item2 = new Item(2000, 900, 55, 5, null, categoryService.findCategoryByName("SUB2"));
//        itemService.saveItem(item2);
//
//        Item item3 = new Item(3000, 900, 55, 5, null, categoryService.findCategoryByName("SUB1-1"));
//        itemService.saveItem(item3);
//
//        Item item4 = new Item(4000, 900, 55, 5, null, categoryService.findCategoryByName("SUB1-2"));
//        itemService.saveItem(item4);
//
//        Item item5 = new Item(1000, 900, 55, 5, null, categoryService.findCategoryByName("SUB1"));
//        itemService.saveItem(item5);
//
//        Item item6 = new Item(2000, 900, 55, 5, null, categoryService.findCategoryByName("SUB2"));
//        itemService.saveItem(item6);
//
//        Item item7 = new Item(3000, 900, 55, 5, null, categoryService.findCategoryByName("SUB1-1"));
//        itemService.saveItem(item7);
//
//        Item item8= new Item(4000, 900, 55, 5, null, categoryService.findCategoryByName("SUB1-2"));
//        itemService.saveItem(item8);
//
//        //when
//
//        List<Item> homeItem = itemService.getHomeItem();
//
//        //then
//
//        assertThat(homeItem.size()).isEqualTo(8);
//
//    }
//
//    @Test
//    @Rollback
//    void testItemService() {
//        //given
//        Item item = new Item(1000, 900, 55, 5, null, categoryService.findCategoryByName("SUB1"));
//        itemService.saveItem(item);
//
//        //when
//        Item findItem = itemService.findItemById(item.getId());
//        assertThat(findItem.getId()).isEqualTo(item.getId());
//
//        itemService.deleteItem(findItem.getId());
//
//        //then
//
//        assertThrows(IllegalStateException.class,()-> itemService.findItemById(findItem.getId()));
//
//    }


}