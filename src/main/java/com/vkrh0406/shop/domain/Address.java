package com.vkrh0406.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String addrDetail;
    private String addrPart1;
    private String addrPart2;
    private String zipcode;

    protected Address(){
    }

    public Address(String addrDetail, String addrPart1, String addrPart2) {
        this.addrDetail = addrDetail;
        this.addrPart1 = addrPart1;
        this.addrPart2 = addrPart2;
    }

    public Address(String addrDetail, String addrPart1, String addrPart2, String zipcode) {
        this.addrDetail = addrDetail;
        this.addrPart1 = addrPart1;
        this.addrPart2 = addrPart2;
        this.zipcode = zipcode;
    }
}
