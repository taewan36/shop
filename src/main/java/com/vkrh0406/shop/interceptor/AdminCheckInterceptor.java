package com.vkrh0406.shop.interceptor;

import com.vkrh0406.shop.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();
        Member member =(Member) session.getAttribute(SessionConst.SESSION_LOGIN_MEMBER);

        if (session == null || member == null) {
            response.sendRedirect("/member/login?redirectURI=" + requestURI);
            return false;
        }
        if (!member.isAdmin()) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
