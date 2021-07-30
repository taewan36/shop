package com.vkrh0406.shop.service;


import com.vkrh0406.shop.domain.Category;
import com.vkrh0406.shop.dto.CategoryDto;
import com.vkrh0406.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public static CategoryDto category;



    //DB에 있는 카테고리를 메모리에 dto상태로 올려놓기
    public void updateCategory(){
        this.category= createCategoryRoot();
    }





    public Category findCategoryByName(String name){
        Category category = categoryRepository.findCategoryByName(name).orElseThrow(() -> new IllegalStateException("이런 이름의 카테고리가 없습니다"));
        return category;
    }



    //
    public CategoryDto createCategoryRoot(){
      //  log.info("createCategoryRoot 실행");
        Map<Long, List<CategoryDto>> groupingByParent = categoryRepository.findAll()
                .stream()
                .map(ce -> new CategoryDto(ce.getId(), ce.getName(), ce.getParentId()))
                .collect(groupingBy(cd -> cd.getParentId()));


        CategoryDto rootCategoryDto = new CategoryDto(0L, "ROOT", null);
        addSubCategories(rootCategoryDto, groupingByParent);

        return rootCategoryDto;
    }

    private void addSubCategories(CategoryDto parent, Map<Long, List<CategoryDto>> groupingByParent) {
        List<CategoryDto> subCategories = groupingByParent.get(parent.getCategoryId());

        //종료 조건
        if (subCategories == null) {
            return;
        }

        // 2. sub categories 셋팅
        parent.setSubCategories(subCategories);
        //children id 리스트에 주입
        for (CategoryDto subCategory : subCategories) {
            parent.getChildrenIds().add(subCategory.getCategoryId());
        }

        // 재귀적으로 subCategories 들에 대해서도 수행
        subCategories.stream()
                .forEach(s -> {
                    addSubCategories(s, groupingByParent);
                   // log.info("subcategory {}",s.getCategoryName());
                });
    }


}
