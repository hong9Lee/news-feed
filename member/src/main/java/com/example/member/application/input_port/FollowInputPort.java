package com.example.member.application.input_port;

import com.example.member.application.output_port.FollowOutPutPort;
import com.example.member.application.output_port.MemberOutPutPort;
import com.example.member.application.usecase.FollowUseCase;
import com.example.member.domain.model.entity.Follow;
import com.example.member.domain.model.entity.Member;
import com.example.member.framework.web.dto.follow.FollowInPutDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FollowInputPort implements FollowUseCase {

    private final FollowOutPutPort followOutPutPort;
    private final MemberOutPutPort memberOutPutPort;

    @Override
    public void following(FollowInPutDTO followInPutDTO) {

        try {
            // 팔로워와 팔로우 멤버를 찾아온다.
            Member followerMember = memberOutPutPort.findByMemberSeq(
                    followInPutDTO.getFollowerMemberSeq()).orElseThrow(() ->
                    new NoSuchElementException("follower 멤버를 찾을 수 없습니다."));

            Member followingMember = memberOutPutPort.findByMemberSeq(
                    followInPutDTO.getFollowingMemberSeq()).orElseThrow(() ->
                    new NoSuchElementException("following 멤버를 찾을 수 없습니다."));


            // follow 엔티티 저장
            followOutPutPort
                    .save(Follow.builder()
                            .following(followingMember)
                            .follower(followerMember)
                            .build());
        } catch (Exception e) {
            log.error("following error:{}", e);
        }
    }
}
