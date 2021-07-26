package com.vkrh0406.shop.form;

import com.vkrh0406.shop.domain.Address;
import lombok.Data;

@Data
public class PayForm {

    private Long orderId;

    private String username;
    private String deliveryRequest;

    private String zipcode;
    private String addrDetail;
    private String addrPart1;
    private String addrPart2;

    public PayForm(String username) {
        this.username = username;
    }
}
