package com.example.member.application.input_port;

import com.example.member.application.output_port.FollowOutPutPort;
import com.example.member.application.output_port.MemberOutPutPort;
import com.example.member.application.output_port.elastic.EsFollowOutPutPort;
import com.example.member.application.usecase.FollowUseCase;
import com.example.member.domain.model.entity.Follow;
import com.example.member.domain.model.entity.Member;
import com.example.member.domain.model.entity.elastic.EsFollow;
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
    private final EsFollowOutPutPort esFollowOutPutPort;

    @Override
    public void following(FollowInPutDTO followInPutDTO) {

        try {
            // 팔로워와 팔로우 멤버를 찾아온다.
            Member member = memberOutPutPort.findByMemberSeq(
                    followInPutDTO.getMemberSeq()).orElseThrow(() ->
                    new NoSuchElementException("following 멤버를 찾을 수 없습니다."));

            Member followerMember = memberOutPutPort.findByMemberSeq(
                    followInPutDTO.getFollowerMemberSeq()).orElseThrow(() ->
                    new NoSuchElementException("follower 멤버를 찾을 수 없습니다."));

            if(followOutPutPort.existsByMemberSeqAndFollowerSeq(member.getSeq(), followerMember.getSeq())) {
                throw new IllegalArgumentException("이미 팔로우 하고 있는 멤버입니다.");
            }

            // follow 엔티티 저장
            followOutPutPort
                    .save(Follow.builder()
                            .member(member)
                            .follower(followerMember)
                            .build());

            updateEsFollow(member, followerMember);
        } catch (Exception e) {
            log.error("following error:{}", e);
        }
    }

    private void updateEsFollow(Member member, Member followerMember) {
        var esMemberFollow = esFollowOutPutPort.findByMemberSeq(member.getSeq())
                .orElse(EsFollow.builder()
                                .memberSeq(member.getSeq())
                                .build());

        var esFollowMemberFollow = esFollowOutPutPort.findByMemberSeq(followerMember.getSeq())
                .orElse(EsFollow.builder()
                        .memberSeq(followerMember.getSeq())
                        .build());

        esMemberFollow.addFollowers(followerMember.getSeq());
        esFollowMemberFollow.addFollowings(member.getSeq());
        esFollowOutPutPort.saveAll(esMemberFollow, esFollowMemberFollow);
    }
}
