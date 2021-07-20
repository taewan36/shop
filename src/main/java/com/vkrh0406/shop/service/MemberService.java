package com.vkrh0406.shop.service;

import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;


    public Member saveMember(Member member) {

        Member findMember = memberRepository.findMemberByLoginId(member.getLoginId()).orElse(null);
        if (findMember != null) {
            throw new IllegalStateException("로그인 아이디 중복회원이 있습니다");
        }

        return memberRepository.save(member);

    }



}
