package com.vkrh0406.shop.Controller;

import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.dto.OrderDto;
import com.vkrh0406.shop.form.OrderForm;
import com.vkrh0406.shop.resolver.Login;
import com.vkrh0406.shop.resolver.SessionCart;
import com.vkrh0406.shop.search.OrderSearch;
import com.vkrh0406.shop.service.CategoryService;
import com.vkrh0406.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;


    @PostMapping("new")
    @ResponseBody
    public Object order(@Login Member member, @RequestBody List<OrderForm> orderForms, HttpSession session) {
        //로그인 상태체크
        if (member == null) {
            return makeErrorCode("NOT_LOGIN", "구매는 로그인이 필요합니다.");
        }


        if (orderForms == null) {
            return makeErrorCode("NULL", "카트가 비었습니다");
        }

        try {
            orderService.makeOrder(orderForms, member,session);
        } catch (IllegalStateException e) {
            return makeErrorCode("IllegalStateException", e.getMessage());
        }

        Map<String, Object> successCode = new HashMap<>();
        successCode.put("code", "OK");
        successCode.put("message", "오더");


        return successCode;
    }

    @GetMapping("list")
    public String orderList(@Login Member member, Model model, @SessionCart Cart cart, @ModelAttribute OrderSearch orderSearch) {

        //헤더
        model.addAttribute("category", CategoryService.category);
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());
        if (member != null) {
            model.addAttribute("username", member.getUsername());
        }

        List<OrderDto> orderDtos = orderService.findAllOrder();

        model.addAttribute("orderDto", orderDtos);

        return "order/orderList";

    }

    private Object makeErrorCode(String code, String message) {
        Map<String, Object> errorCode = new HashMap<String, Object>();
        errorCode.put("code", code);
        errorCode.put("message", message);

        return errorCode;
    }
}
