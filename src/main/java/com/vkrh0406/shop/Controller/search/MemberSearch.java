package com.vkrh0406.shop.Controller.search;

import lombok.Data;

@Data
public class MemberSearch {
    private String searchType;
    private String keyword;
    private String memberName;
    private String loginId;

    public void checkSearchType(){
        if(this.getSearchType().equals("memberName")){
            this.setMemberName(this.getKeyword());
        }
        else if(this.getSearchType().equals("loginId")){
            this.setLoginId(this.getKeyword());
        }

    }

}
