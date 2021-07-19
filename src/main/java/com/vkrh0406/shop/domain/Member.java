package com.vkrh0406.shop.domain;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;

    private String username;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Order> orderList=new ArrayList<>();

    public Member(String loginId, String password, String username) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;

    }

    protected Member() {
    }
}
