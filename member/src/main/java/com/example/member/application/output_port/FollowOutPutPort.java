package com.example.member.application.output_port;

import com.example.member.domain.model.entity.Follow;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowOutPutPort {

    Follow save(Follow follow);

    boolean existsByMemberSeqAndFollowerSeq(long memberSeq, long followerSeq);
}
