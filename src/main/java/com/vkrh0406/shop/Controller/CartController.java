package com.vkrh0406.shop.Controller;



import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.domain.OrderItem;
import com.vkrh0406.shop.dto.CartDto;
import com.vkrh0406.shop.dto.OrderItemDto;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.resolver.Login;
import com.vkrh0406.shop.resolver.SessionCart;
import com.vkrh0406.shop.service.CartService;
import com.vkrh0406.shop.service.CategoryService;
import com.vkrh0406.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MemberService memberService;



    //카트에 아이템 추가
    @GetMapping("add/{itemId}")
    public String addCart(@PathVariable Long itemId, HttpServletRequest request, @RequestParam(required = false) String requestURI,
                          @Login Member member
    ) {
        HttpSession session = request.getSession();

        //로그인 상태인경우
        if (member != null) {
            Member findMember = memberService.findById(member.getId());
            Cart cart = cartService.saveOrderItemToCart(findMember, itemId);

            //Lazy 로딩이므로 미리 필요한 데이터를 채워서 세션에 올리기 위해 foreach 문을 돌립니다.
            cart.getOrderItems()
                    .stream()
                    .forEach(orderItem -> {
                        orderItem.getItem().getUploadFile().getStoreFileName();
                       // log.info("아이템 이름={}",item.getName());
                    });
           // log.info("영속성인가?? {}",cart.getOrderItems().get(0).getItem().getName());

            session.setAttribute(SessionConst.SESSION_CART, cart);

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

    //카트리스트
    @GetMapping("list")
    public String cartList(@SessionCart Cart cart, @Login Member member, Model model) {
        if (member != null) {
            model.addAttribute("username", member.getUsername());

        }

        //카트Dto 만들기
        CartDto cartDto = cartService.makeCartDto(cart);


        model.addAttribute("cartDto", cartDto);
        model.addAttribute("category", CategoryService.category);
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());
        return "cart/cartList";
    }

    //카트에서 수량 바꾸면 업데이트 해줌
    @PostMapping("update")
    @ResponseBody
    public String updateCart(@SessionCart Cart cart, @RequestParam("orderItemId") Long itemId, @RequestParam int count,
                             @Login Member member) {
        if (member != null) {
            cartService.updateCartOrderItemCount(cart, itemId, count);
        }

        cart.getOrderItems()
                .stream()
                .forEach(o -> {
                    if (o.getItem().getId().equals(itemId)) {
                        o.setCount(count);
                    }
                });

        return "ok";

    }
}
