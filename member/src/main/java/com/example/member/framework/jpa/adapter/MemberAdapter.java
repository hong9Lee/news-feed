package com.example.member.framework.jpa.adapter;

import com.example.member.application.output_port.MemberOutPutPort;
import com.example.member.domain.model.entity.Member;
import com.example.member.framework.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberAdapter implements MemberOutPutPort {

    private final MemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findByMemberSeq(long memberSeq) {
        return memberRepository.findById(memberSeq);
    }
}
