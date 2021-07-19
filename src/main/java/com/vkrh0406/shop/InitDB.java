package com.vkrh0406.shop;

import com.vkrh0406.shop.domain.Category;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.domain.UploadFile;
import com.vkrh0406.shop.repository.CategoryRepository;
import com.vkrh0406.shop.repository.ItemRepository;
import com.vkrh0406.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void initCategories() {
        initService.initCategory();
        initService.initItemDB();
    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final CategoryRepository categoryRepository;
        private final CategoryService categoryService;
        private final ItemRepository itemRepository;


        public void initItemDB() {

            List<Item> all = itemRepository.findAll();
            if (all.size() > 0) {
                return;
            }

            Item item = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "images/book1.jpg"), categoryService.findCategoryByName("게임")
                    , "로블록스 게임제작 무작정 따라하기");
            Item item2 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "images/book2.jpg"), categoryService.findCategoryByName("게임"),
                    "DEVELOPING 2D GAMES WITH UNITY");
            Item item3 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "images/book3.jpg"), categoryService.findCategoryByName("게임"),
                    "유니티 2D 게임 제작");
            Item item4 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "images/book4.jpg"), categoryService.findCategoryByName("게임"),
                    "로호의 배경 일러스트 메이킹");
            Item item5 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "images/book5.jpg"), categoryService.findCategoryByName("게임"),
                    "인생 유니티 VR/AR 교과서");
            Item item6 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "images/book6.jpg"), categoryService.findCategoryByName("게임"),
                    "언리얼 엔진 4");
            Item item7 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "images/book7.jpg"), categoryService.findCategoryByName("게임"),
                    "파이썬으로 배우는 게임 개발 실전편");
            Item item8 = new Item(5000, 2000, 55, 5,
                    new UploadFile("업로드.png", "images/book8.jpg"), categoryService.findCategoryByName("게임"),
                    "게임 프로그래머로 산다는 것");

            itemRepository.save(item);
            itemRepository.save(item2);
            itemRepository.save(item3);
            itemRepository.save(item4);
            itemRepository.save(item5);
            itemRepository.save(item6);
            itemRepository.save(item7);
            itemRepository.save(item8);


        }


        //카테고리 DB
        public void initCategory() {


            List<Category> all = categoryRepository.findAll();

            Long sequance_id = 0L;

            if (all.size() >= 1) {
                return;
            }

            Category category1 = new Category("IT 모바일", 1L, 0L);
            categoryRepository.save(category1);

            Category category2 = new Category("자연과학", 2L, 0L);
            categoryRepository.save(category2);

            Category category3 = new Category("자기계발", 3L, 0L);
            categoryRepository.save(category3);

            Category category4 = new Category("게임", 4L, 1L);
            categoryRepository.save(category4);

            Category category5 = new Category("공학", 5L, 2L);
            categoryRepository.save(category5);

            Category category6 = new Category("처세술/삶의 자세", 6L, 3L);
            categoryRepository.save(category6);


        }


    }

}
