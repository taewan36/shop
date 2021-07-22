package com.vkrh0406.shop.Controller;



import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.resolver.Login;
import com.vkrh0406.shop.service.CartService;
import com.vkrh0406.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MemberService memberService;


    @GetMapping("add/{itemId}")
    public String addCart(@PathVariable Long itemId, HttpServletRequest request, @RequestParam(required = false) String requestURI,
                          @Login Member member
                          ) {
        HttpSession session = request.getSession();

        //로그인 상태인경우
        if (member != null) {
            Member findMember = memberService.findById(member.getId());
            Cart cart = cartService.saveOrderItemToCart(findMember, itemId);
            session.setAttribute(SessionConst.SESSION_CART,cart);

            if (requestURI != null) {
                return "redirect:" + requestURI;
            }
            return "redirect:/";
        }



        Cart cart = (Cart) session.getAttribute(SessionConst.SESSION_CART);

        log.info("카트 {}", cart);

        if (cart == null) {
            cart = new Cart();

        }

        Cart sessionCart = cartService.saveOrderItemToCart(cart, itemId);

        session.setAttribute(SessionConst.SESSION_CART, sessionCart);


        if (requestURI != null) {
            return "redirect:" + requestURI;
        }
        return "redirect:/";

    }
}
