package com.vkrh0406.shop.repository;

import com.vkrh0406.shop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    public Optional<Member> findMemberById(Long id);




}
