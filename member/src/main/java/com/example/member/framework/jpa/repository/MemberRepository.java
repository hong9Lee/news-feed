package com.example.member.framework.jpa.repository;

import com.example.member.domain.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
