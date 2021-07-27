package com.vkrh0406.shop.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.domain.Order;
import com.vkrh0406.shop.domain.OrderStatus;
import com.vkrh0406.shop.dto.OrderDto;
import com.vkrh0406.shop.form.OrderForm;
import com.vkrh0406.shop.form.PayForm;
import com.vkrh0406.shop.jsonparse.AccessToken;
import com.vkrh0406.shop.resolver.Login;
import com.vkrh0406.shop.resolver.SessionCart;
import com.vkrh0406.shop.search.OrderSearch;
import com.vkrh0406.shop.service.CategoryService;
import com.vkrh0406.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;


    //생성한 오더 결제 전 페이지
    @GetMapping("{orderId}")
    public String orderBeforePay(@SessionCart Cart cart,Model model,@Login Member member,@PathVariable Long orderId) throws IllegalAccessException {
        if (member != null) {
            model.addAttribute("username", member.getUsername());
            log.info("멤버이름 : {}",member.getUsername());
        }
        //기본적인 헤더 필요한 정보 주입 (카테고리, 카트사이즈)
        model.addAttribute("category", CategoryService.category);
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());

        //오더아이디로 불러오기
        Order findOrder = orderService.findOrderById(orderId);

        //오더 상태체크
        if (findOrder.getOrderStatus() != OrderStatus.PRE_ORDER) {
            throw new IllegalAccessException("결제 전 오더만 결제할 수 있습니다.");
        }

        //오더 가격
        int totalPrice = findOrder.getTotalPrice();
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("orderId", orderId);
        model.addAttribute("orderUuid", findOrder.getUuid());


        return "order/order";
    }


    // 오더 결제
    @PostMapping("pay")
    public String payOrder(@SessionCart Cart cart,Model model,@Login Member member,@ModelAttribute PayForm payForm)  {
        if (member != null) {
            model.addAttribute("username", member.getUsername());
        }
        //기본적인 헤더 필요한 정보 주입 (카테고리, 카트사이즈)
        model.addAttribute("category", CategoryService.category);
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());

        Long orderId = payForm.getOrderId();
        orderService.payOrder(orderId, payForm);


        return "redirect:/";
    }

    //결제 검증 프로세스
    @PostMapping("payCheck")
    @ResponseBody
    public Object payOrderCheck(@RequestBody String request) throws JsonProcessingException {



        Map<String, Object> message = orderService.payCheckProcess(request);


        return message;
    }



    //카트에서 오더 생성
    @PostMapping("new")
    @ResponseBody
    public Object order(@Login Member member, @RequestBody List<OrderForm> orderForms, HttpSession session, @SessionCart Cart cart) {

        Long orderId;

        //로그인 상태체크
        if (member == null) {
            return makeErrorCode("NOT_LOGIN", "구매는 로그인이 필요합니다.");
        }

        //카트 체크크
        if (cart == null || cart.getOrderItems().size() == 0) {
            return makeErrorCode("NULL", "카트가 비었습니다");
        }

        try {
            orderId = orderService.makeOrder(orderForms, member, session);
        } catch (IllegalStateException e) {
            return makeErrorCode("IllegalStateException", e.getMessage());
        }

        Map<String, Object> successCode = new HashMap<>();
        successCode.put("code", "OK");
        successCode.put("message", "오더");
        successCode.put("orderId", orderId);


        return successCode;
    }

    //주문목록 뽑기
    @GetMapping("list")
    public String orderList(@Login Member member, Model model, @SessionCart Cart cart, @ModelAttribute OrderSearch orderSearch) {

        //헤더
        model.addAttribute("category", CategoryService.category);
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());
        if (member != null) {
            model.addAttribute("username", member.getUsername());
        }

        List<OrderDto> orderDtos = orderService.findAllByMemberId(member);

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
