package com.vkrh0406.shop.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Item extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private int price;

    private int quantity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;




    //아이템 총 가격 계산
    public int getTotalPrice() {
        if (quantity <= 0) {
            return 0;
        }
        return price * quantity;
    }




}
