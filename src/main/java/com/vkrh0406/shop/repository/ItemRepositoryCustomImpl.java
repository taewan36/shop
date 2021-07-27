package com.vkrh0406.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vkrh0406.shop.Controller.ItemSearch;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.dto.CategoryDto;
import com.vkrh0406.shop.dto.ItemDto;
import com.vkrh0406.shop.dto.QItemDto;
import com.vkrh0406.shop.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.vkrh0406.shop.domain.QItem.item;
import static org.springframework.util.StringUtils.hasText;

@Repository
@Slf4j
@Transactional(readOnly = true)
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    //이 카테고리에 대한 칠드런id 들을 얻기 위한 재귀함수.. 사실 여기보단 카테고리서비스에 있는게 맞는거겠지만 일단 여기 두었습니다
    private void findCategoryChildren(CategoryDto categoryDto, List<Long> ids, Long categoryId) {
        List<CategoryDto> subCategories = categoryDto.getSubCategories();

        if (subCategories == null) {
            return;
        }

        for (CategoryDto subCategory : subCategories) {
            //해당 카테고리 찾았으면 칠드런 id가지고 나옴
            if (subCategory.getCategoryId().equals(categoryId)) {

                List<Long> childrenIds = subCategory.getChildrenIds();
                for (Long childrenId : childrenIds) {
                    ids.add(childrenId);
                }
                ids.add(categoryId);


                return;
            }
            //못 찾았으면 하위 카테고리들도 검색해보기위해 재귀함수에 넣음
            else{
              //  log.info("카테고리 못찾음 {}", subCategory.getCategoryName());
                findCategoryChildren(subCategory, ids, categoryId);
            }
        }

    }

    //카테고리별 검색할때 사용함!
    @Transactional
    public Page<ItemDto> searchItemWithCategory(ItemSearch itemSearch, Pageable pageable, Long categoryId) {

        List<Long> categoryIds = new ArrayList<>();

        //하위 카테고리 아이디 포함해서 다 찾기.
        findCategoryChildren(CategoryService.category, categoryIds, categoryId);

        List<ItemDto> content = queryFactory
                .select(new QItemDto(item.id,item.name,item.price,item.discountPrice,item.ratingStar,item.uploadFile.storeFileName))
                .from(item)
                .orderBy(item.id.desc())
                .where(categoryCondition(categoryIds),searchCondition(itemSearch.getItemName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Item> countQuery = queryFactory
                .selectFrom(item)
                .where(categoryCondition(categoryIds),searchCondition(itemSearch.getItemName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

    }

    //findCategoryItems 에서 사용하는 BooleanBuilder
    private BooleanBuilder categoryCondition(List<Long> categoryIds) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        //이 카테고리 id 들만
        for (Long categoryId : categoryIds) {
            booleanBuilder.or(item.category.id.eq(categoryId));
           // log.info("불린빌더 {}", categoryId);
        }

        return booleanBuilder;

    }


    //모든 아이템 검색할때 쓰는 함수
    @Transactional
    public Page<ItemDto> searchItem(ItemSearch itemSearch, Pageable pageable) {

        List<ItemDto> content = queryFactory
                .select(new QItemDto(item.id,item.name,item.price,item.discountPrice,item.ratingStar,item.uploadFile.storeFileName))
                .from(item)
                .orderBy(item.id.desc())
                .where(searchCondition(itemSearch.getItemName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Item> countQuery = queryFactory
                .selectFrom(item)
                .where(searchCondition(itemSearch.getItemName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

    }

    private BooleanBuilder searchCondition(String name) {


        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(name)) {
            booleanBuilder.or(item.name.contains(name));
        }


        return booleanBuilder;

    }

    private BooleanExpression nameContain(String name) {
        return (hasText(name)) ? item.name.contains(name) : null;
    }




}
