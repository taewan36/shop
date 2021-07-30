package com.vkrh0406.shop.Controller.search;

import lombok.Data;

@Data
public class ItemSearch {
    private String searchType;
    private String keyword;
    private String itemName;
    private String categoryName;


    public void checkSearchType(){
        if(this.getSearchType().equals("itemName")){
            this.setItemName(this.getKeyword());
        }
        else if(this.getSearchType().equals("categoryName")){
            this.setCategoryName(this.getKeyword());
        }

    }

}
