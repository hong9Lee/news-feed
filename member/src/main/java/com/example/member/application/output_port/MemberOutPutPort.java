package com.example.member.application.output_port;

import com.example.member.domain.model.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberOutPutPort {
    Member save(Member member);

    Optional<Member> findByMemberSeq(long memberSeq);
}
