package com.vkrh0406.shop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    //정상가격
    private int price;
    //세일된 가격
    private int discountPrice;

    private int quantity;

    //평점 별 개수
    private int ratingStar;


    @Embedded
    private UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    public Item(int price, int discountPrice, int quantity, int ratingStar, UploadFile uploadFile, Category category,String name) {
        this.price = price;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
        this.ratingStar = ratingStar;
        this.uploadFile = uploadFile;
        this.category = category;
        this.name = name;
    }

    public Item(int price, int discountPrice, int quantity, int ratingStar, UploadFile uploadFile, Category category) {
        this.price = price;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
        this.ratingStar = ratingStar;
        this.uploadFile = uploadFile;
        this.category = category;
    }

    //아이템 총 가격 계산
    public int getTotalPrice() {
        if (quantity <= 0) {
            return 0;
        }
        return price * quantity;
    }


    public void addStock(int count) {
        this.quantity += count;
    }

    public void removeQuantity(int quantity) {
        int restStock = this.quantity - quantity;
        if (restStock < 0) {
            throw new IllegalStateException("need more quantity");
        }

        this.quantity = restStock;
    }

   public int getRealPrice(){
       return (discountPrice != 0) ? discountPrice : price;
   }
}
