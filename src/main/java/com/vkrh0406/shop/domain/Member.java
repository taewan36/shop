package com.vkrh0406.shop.domain;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;

    private String username;

    private boolean isAdmin;


    @Embedded
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "member")
    private Cart cart;


    public Member(String loginId, String password, String username, Address address) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.address = address;

    }

    protected Member() {
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
