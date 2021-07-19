package com.vkrh0406.shop.service;


import com.vkrh0406.shop.dto.CategoryDto;
import com.vkrh0406.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

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

        // 재귀적으로 subCategories 들에 대해서도 수행
        subCategories.stream()
                .forEach(s -> {
                    addSubCategories(s, groupingByParent);
                   // log.info("subcategory {}",s.getCategoryName());
                });
    }


}
