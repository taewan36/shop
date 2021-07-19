package com.vkrh0406.shop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @Column(name = "category_id")
    private Long id;

    private String name;
    private Long parentId;

    public Category(String name, Long id, Long parentId) {
        this.name = name;
        this.id=id;
        this.parentId = parentId;
    }
}
