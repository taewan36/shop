package com.vkrh0406.shop.jsonparse;

import lombok.Data;

@Data
public class AccessToken {
    private String code;
    private String message;
    private Response response;


}
