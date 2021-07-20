package com.vkrh0406.shop.Controller;

import lombok.Data;

@Data
public class ItemSearch {
    private String searchType;
    private String keyword;
    private String itemName;
    private String content;
    private String writer;

    public void checkSearchType(){
        if(this.getSearchType().equals("title")){
            this.setItemName(this.getKeyword());
        }
        else if(this.getSearchType().equals("writer")){
            this.setWriter(this.getKeyword());
        }
        else if(this.getSearchType().equals("content")){
            this.setContent(this.getKeyword());
        }
        else if(this.getSearchType().equals("titleAndContent")){
            this.setItemName(this.getKeyword());
            this.setContent(this.getKeyword());
        } else if (this.getSearchType().equals("all")) {
            this.setItemName(this.getKeyword());
            this.setContent(this.getKeyword());
            this.setWriter(this.getKeyword());
        }
    }

}
