package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.domain.OrderItem;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.repository.CartRepository;
import com.vkrh0406.shop.repository.ItemRepository;
import com.vkrh0406.shop.repository.MemberRepository;
import com.vkrh0406.shop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;


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


    @Transactional
    public void login(String loginId, String password, HttpSession httpSession, Cart cart) {

        Member findMember = memberRepository.findMemberByLoginId(loginId).orElseThrow(() -> new IllegalStateException("이런 로그인 아이디는 없습니다."));

        if (findMember.getPassword().equals(password)) {
            httpSession.setAttribute(SessionConst.SESSION_LOGIN_MEMBER, findMember);

            //멤버에 소속된 카트가 없는데 세션에는 카트가 있으면 멤버에 등록됨
            if (findMember.getCart() == null && cart != null) {
                Cart savedCart = cartRepository.save(cart);
                savedCart.setMember(findMember);
                savedCart.getOrderItems()
                        .stream()
                        .forEach(o->{
                            o.getItem();
                                }
                        );
                findMember.setCart(savedCart);
            }
            //멤버에 소속된 카트가 없는데 세션도 카트 없으면 새로운 카트 생성
            else if (findMember.getCart() == null && cart == null) {
                Cart savedCart = cartRepository.save(new Cart());
                savedCart.setMember(findMember);
                savedCart.getOrderItems()
                        .stream()
                        .forEach(o->{
                                    o.getItem();
                                }
                        );

                findMember.setCart(savedCart);

                httpSession.setAttribute(SessionConst.SESSION_CART, findMember.getCart());
            }
            //멤버에 소속된 카트가 있는데 세션에 카트가 없는경우
            else if (findMember.getCart() != null && cart == null) {
                Cart cart1 = findMember.getCart();
                cart1.getOrderItems()
                        .stream()
                        .forEach(o->{
                                    o.getItem();
                                }
                        );

                httpSession.setAttribute(SessionConst.SESSION_CART,cart1);
            }
            //멤버 카트 !=null 세션카트 !=null 일경우 세션카트에서 멤버카트로 추가
            else if (findMember.getCart() != null && cart != null) {
                Cart findCart = findMember.getCart();


                //기존 카트에서 오더아이템을 꺼내서 기존 카트에 넣는다
                List<OrderItem> sessionOrderItems = cart.getOrderItems();

                //SessionOrderItem에 db에 등록된 아이템 제대로 설정하기
                sessionOrderItems.stream()
                        .forEach(o->{
                            o.setItem(itemRepository.findById(o.getItem().getId()).orElseThrow(()-> new IllegalStateException("이 아이템 id는 존재하지 않습니다.")));
                        });

                //이중 for문으로 회원의 카트db에 동일한 아이템이 있으면 숫자만 추가
                for (OrderItem sessionOrderItem : sessionOrderItems) {
                    boolean sameOrderItem=false;
                    for (OrderItem orderItem : findCart.getOrderItems()) {
                        if (orderItem.getItem().getId().equals(sessionOrderItem.getItem().getId())) {
                            orderItem.addCount(sessionOrderItem.getCount());
                            sameOrderItem=true;
                        }
                    }
                    // 동일한 아이템이 없을경우 orderItem 추가
                    if (sameOrderItem == false) {
                        findCart.addOrderItem(sessionOrderItem);
                    }
                }

                //Lazy 로딩이므로 데이터를 여기서 불러옴
                findCart.getOrderItems()
                        .stream()
                        .forEach(o ->{
                            o.getItem().getUploadFile();
                        });

                httpSession.setAttribute(SessionConst.SESSION_CART, findCart);

            }
        }
        //비번 틀려서 로그인 실패
        else{
            throw new IllegalStateException("아이디 또는 패스워드가 다릅니다.");

        }


    }
}
