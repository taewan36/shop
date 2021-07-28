package com.vkrh0406.shop.repository;

import com.vkrh0406.shop.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member,Long> {

    public Optional<Member> findMemberById(Long id);


    public Optional<Member> findMemberByLoginId(String loginId);

    public Page<Member> findAll(Pageable pageable);




}
