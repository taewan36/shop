package com.vkrh0406.shop.Controller;

import com.vkrh0406.shop.domain.Member;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
