package com.vkrh0406.shop.Controller;

import com.vkrh0406.shop.domain.Address;
import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.form.LoginForm;
import com.vkrh0406.shop.form.MemberForm;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.resolver.SessionCart;
import com.vkrh0406.shop.service.CategoryService;
import com.vkrh0406.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("new")
    public String newMemberForm(Model model, @SessionCart Cart cart){


        model.addAttribute("category", CategoryService.category);
        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());

        return "member/createMemberForm";
    }


    @PostMapping("new")
    public String newMember(Model model, @SessionCart Cart cart, @ModelAttribute @Valid MemberForm memberForm, BindingResult bindingResult){


        model.addAttribute("category", CategoryService.category);
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());


        if (bindingResult.hasErrors()) {
            return "member/createMemberForm";
        }

        Member member = new Member(memberForm.getLoginId(), memberForm.getPassword(), memberForm.getName(),
                new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode()));


        try {
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            bindingResult.reject("로그인 아이디 중복","로그인 아이디 중복입니다.");
            return "member/createMemberForm";
        }



        return "redirect:/";
    }


    @GetMapping("login")
    public String loginForm(Model model, @SessionCart Cart cart){

        model.addAttribute("category", CategoryService.category);
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());

        return "member/loginForm";
    }

}
