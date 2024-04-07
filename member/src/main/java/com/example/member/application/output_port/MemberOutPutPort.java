package com.example.member.application.output_port;

import com.example.member.domain.model.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberOutPutPort {
    Member save(Member member);
}
