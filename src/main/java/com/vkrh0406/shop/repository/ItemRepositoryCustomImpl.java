package com.vkrh0406.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vkrh0406.shop.Controller.ItemSearch;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.dto.ItemDto;
import com.vkrh0406.shop.dto.QItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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

    @Transactional
    public Page<ItemDto> searchItem(ItemSearch itemSearch, Pageable pageable) {

        List<ItemDto> content = queryFactory
                .select(new QItemDto(item.id,item.name,item.price,item.price,item.ratingStar,item.uploadFile.storeFileName))
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
//        if (hasText(title)) {
//            booleanBuilder.or(item.name.contains(title));
//        }
//        if (hasText(title)) {
//            booleanBuilder.or(item.name.contains(title));
//        }


        return booleanBuilder;

    }

    private BooleanExpression nameContain(String name) {
        return (hasText(name)) ? item.name.contains(name) : null;
    }




}
