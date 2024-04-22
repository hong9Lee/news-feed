package com.example.member.framework.jpa.adapter;

import com.example.member.application.output_port.FollowOutPutPort;
import com.example.member.domain.model.entity.Follow;
import com.example.member.framework.jpa.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowAdapter implements FollowOutPutPort {

    private final FollowRepository followRepository;

    @Override
    public Follow save(Follow follow) {
        return followRepository.save(follow);
    }

    @Override
    public boolean existsByMemberSeqAndFollowerSeq(long memberSeq, long followerSeq) {
        return followRepository.existsByMemberSeqAndFollowerSeq(memberSeq, followerSeq);
    }
}
