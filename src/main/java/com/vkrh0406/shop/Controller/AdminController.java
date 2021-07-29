package com.vkrh0406.shop.Controller;

import com.vkrh0406.shop.domain.DeliveryStatus;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.domain.OrderStatus;
import com.vkrh0406.shop.dto.OrderDto;
import com.vkrh0406.shop.resolver.Login;
import com.vkrh0406.shop.service.AdminService;
import com.vkrh0406.shop.service.MemberService;
import com.vkrh0406.shop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;


    //홈
    @GetMapping("home")
    public String home(@Login Member member, Model model) {
        model.addAttribute("username", member.getUsername());

        return "admin/home";
    }

    //멤버 목록
    @GetMapping("member/list")
    public String getMemberList(@Login Member member, Model model, @PageableDefault(size = 10) Pageable pageable) {

        //유저네임
        model.addAttribute("username", member.getUsername());


        Page<Member> memberList = adminService.getMemberList(member, pageable);

        model.addAttribute("memberList", memberList);

        return "admin/member/list";
    }

    //멤버 삭제
    @GetMapping("member/delete/{memberId}")
    public String deleteMember(@PathVariable Long memberId, @Login Member member) {

        adminService.deleteMember(member, memberId);


        return "redirect:/admin/member/list";
    }

    //오더리스트
    @GetMapping("order/list")
    public String getOrderList(@Login Member member, Model model, @PageableDefault(size = 10) Pageable pageable) {
        //유저네임
        model.addAttribute("username", member.getUsername());

        Page<OrderDto> orderDto = adminService.getOrderDtoList(member, pageable);

        model.addAttribute("orderDto", orderDto);


        return "admin/order/list";

    }

    //결제한 오더리스트
    @GetMapping("order/orderedList")
    public String getOrderedOrderList(@Login Member member, Model model, @PageableDefault(size = 10) Pageable pageable) {
        //유저네임
        model.addAttribute("username", member.getUsername());

        Page<OrderDto> orderDto = adminService.getOrderedOrderDtoList(member, pageable);

        model.addAttribute("orderDto", orderDto);


        return "admin/order/orderedList";

    }


    //오더 상태 업데이트
    @PostMapping("order/updateOrderStatus")
    @ResponseBody
    public Map<String, Object> updateOrderStatus(@Login Member member, @RequestParam Long orderId, @RequestParam OrderStatus orderStatus) {



        adminService.updateOrderStatus(member, orderId, orderStatus);

        Map<String, Object> successMessage = new HashMap<>();
        successMessage.put("code", "success");
        successMessage.put("message", "오더 상태 업데이트 성공");

        return successMessage;
    }

    //배송 상태 업데이트
    @PostMapping("order/updateDeliveryStatus")
    @ResponseBody
    public Map<String, Object> updateDeliveryStatus(@Login Member member, @RequestParam Long orderId, @RequestParam DeliveryStatus deliveryStatus) {



        adminService.updateDeliveryStatus(member, orderId, deliveryStatus);

        Map<String, Object> successMessage = new HashMap<>();
        successMessage.put("code", "success");
        successMessage.put("message", "오더 상태 업데이트 성공");

        return successMessage;
    }

    @GetMapping("order/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId, @Login Member member) {

        adminService.deleteOrder(member, orderId);

        return "redirect:/admin/order/list";
    }
}
