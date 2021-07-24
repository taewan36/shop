package com.vkrh0406.shop.Controller;

import com.vkrh0406.shop.domain.Address;
import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.form.LoginForm;
import com.vkrh0406.shop.form.MemberForm;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.resolver.Login;
import com.vkrh0406.shop.resolver.SessionCart;
import com.vkrh0406.shop.service.CategoryService;
import com.vkrh0406.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String loginForm(Model model, @SessionCart Cart cart, @Login Member member){
        //이미 로그인 상태면
        if (member != null) {
            return "redirect:/";
        }

        model.addAttribute("category", CategoryService.category);
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());

        return "member/loginForm";
    }
    @PostMapping("login")
    public String login(Model model, @SessionCart Cart cart, @Login Member member, @ModelAttribute @Valid LoginForm loginForm,
                        BindingResult bindingResult, HttpServletRequest request,@RequestParam(required = false) String redirectURI){
        if (bindingResult.hasErrors()) {
            return "member/loginForm";
        }
        //이미 로그인 상태면
        if (member != null) {
            return "redirect:/";
        }

        //헤더 카테고리
        model.addAttribute("category", CategoryService.category);
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());

        HttpSession session = request.getSession();

        try {
            memberService.login(loginForm.getLoginId(), loginForm.getPassword(), session, cart);
        } catch (IllegalStateException e) {
            bindingResult.reject("아이디 또는 비번틀림","아이디 또는 비번이 틀립니다.");
            return "member/loginForm";
        }


        return "redirect:/" + ((redirectURI != null) ? redirectURI : "");
    }

    @GetMapping("logout")
    public String logout(Model model, @SessionCart Cart cart, @Login Member member,HttpSession session){
        //로그인 상태가 아니면
        if (member == null) {
            return "redirect:/";
        }
        Cart cart1 = new Cart();
        cart1.setOrderItems(cart.getOrderItems());
        cart1.setSize(cart.getSize());

        //세션 지우기
        session.removeAttribute(SessionConst.SESSION_LOGIN_MEMBER);
        //카트는 영속성 컨텍스트 해제한 카트를 새로 넣어줌
//        session.setAttribute(SessionConst.SESSION_CART,cart1);
        session.removeAttribute(SessionConst.SESSION_CART);



        return "redirect:/";
    }

}
