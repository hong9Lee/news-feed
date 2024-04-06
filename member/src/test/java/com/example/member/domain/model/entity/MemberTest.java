package com.example.member.domain.model.entity;

import com.example.member.framework.jpa.repository.FollowRepository;
import com.example.member.framework.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowRepository followRepository;

    @Test
    void 팔로워_테스트() {
        Member memberFollower1 = Member.builder()
                .name("팔로워1")
                .build();

        Member memberFollower2 = Member.builder()
                .name("팔로워2")
                .build();

        Member user = Member.builder()
                .name("테스트 유저")
                .build();

        Member savedFollower1 = memberRepository.save(memberFollower1);
        Member savedFollower2 = memberRepository.save(memberFollower2);
        Member savedUser = memberRepository.save(user);

        Follow follow1 = Follow.builder()
                .follower(savedFollower1)
                .following(savedUser)
                .build();

        Follow follow2 = Follow.builder()
                .follower(savedFollower2)
                .following(savedUser)
                .build();

        followRepository.save(follow1);
        followRepository.save(follow2);


        List<Follow> followings = followRepository.findByFollowing(savedUser);
        assertEquals(2, followings.size());

        List<Follow> followers = followRepository.findByFollower(savedUser);
        assertEquals(0, followers.size());
    }
}
