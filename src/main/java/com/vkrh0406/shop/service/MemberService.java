package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;


    public Member findById(Long id) {
        return memberRepository.findMemberById(id).orElseThrow(() -> new IllegalStateException("찾는 멤버 아이디가 없습니다"));
    }

    public Member saveMember(Member member) {

        Member findMember = memberRepository.findMemberByLoginId(member.getLoginId()).orElse(null);
        if (findMember != null) {
            throw new IllegalStateException("로그인 아이디 중복회원이 있습니다");
        }

        return memberRepository.save(member);

    }


    public void login(String loginId, String password, HttpSession httpSession, Cart cart) {

        Member findMember = memberRepository.findMemberByLoginId(loginId).orElseThrow(() -> new IllegalStateException("이런 로그인 아이디는 없습니다."));

        if (findMember.getPassword().equals(password)) {
            httpSession.setAttribute(SessionConst.SESSION_LOGIN_MEMBER, findMember);

            //멤버에 소속된 카트가 없는데 세션에는 카트가 있으면 멤버에 등록됨
            if (findMember.getCart() == null && cart != null) {
                findMember.setCart(cart);
            }
            //멤버에 소속된 카트가 없는데 세션도 카트 없으면 새로운 카트 생성
            else if (findMember.getCart() == null && cart == null) {
                findMember.setCart(new Cart());
                httpSession.setAttribute(SessionConst.SESSION_CART, findMember.getCart());
            }
            //멤버에 소속된 카트가 있는데 세션에 카트가 없는경우
            else if (findMember.getCart() != null && cart == null) {
                httpSession.setAttribute(SessionConst.SESSION_CART, findMember.getCart());
            }
            //멤버 카트 !=null 세션카트 !=null 일경우 세션카트에서 멤버카트로 추가
            else if (findMember.getCart() != null && cart != null) {

                cart.getOrderItems()
                        .stream()
                        .forEach(o -> {
                            findMember.getCart().getOrderItems().add(o);});
                httpSession.setAttribute(SessionConst.SESSION_CART, findMember.getCart());
            }
        }


    }
}
