package com.vkrh0406.shop;

import com.vkrh0406.shop.domain.Category;
import com.vkrh0406.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitCategory {

    private final InitService initService;

    @PostConstruct
    public void initCategories(){
        initService.initCategory();
    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{

        private final CategoryRepository categoryRepository;



        public void initCategory(){


            List<Category> all = categoryRepository.findAll();

            Long sequance_id=0L;

            if (all.size() >= 1) {
                return;
            }

            Category category1 = new Category("IT 모바일", 1L,0L);
            categoryRepository.save(category1);

            Category category2 = new Category("자연과학", 2L,0L);
            categoryRepository.save(category2);

            Category category3 = new Category("자기계발", 3L,0L);
            categoryRepository.save(category3);

            Category category4 = new Category("게임", 4L,1L);
            categoryRepository.save(category4);

            Category category5 = new Category("공학", 5L,2L);
            categoryRepository.save(category5);

            Category category6 = new Category("처세술/삶의 자세", 6L,3L);
            categoryRepository.save(category6);


        }


    }

}
