package com.vkrh0406.shop.Controller;



import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.service.CartService;
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


    @GetMapping("add/{itemId}")
    public String addCart(@PathVariable Long itemId, HttpServletRequest request, @RequestParam(required = false) String requestURI) {
        HttpSession session = request.getSession(false);



        if (session == null) {
            session = request.getSession();
        }

        Cart cart = (Cart) session.getAttribute(SessionConst.SESSION_CART);

        log.info("카트 {}", cart);

        if (cart == null) {
            cart = new Cart();
            Long cartId = cartService.saveCart(cart);
            log.info("새로만든 카트 {}", cartId);

        }

        Cart sessionCart = cartService.saveOrderItemToCart(cart.getId(), itemId);

        session.setAttribute(SessionConst.SESSION_CART, sessionCart);


        if (requestURI != null) {
            return "redirect:" + requestURI;
        }
        return "redirect:/";

    }
}
