package com.example.member.framework.jpa.repository;

import com.example.member.domain.model.entity.Follow;
import com.example.member.domain.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowing(Member following);
    List<Follow> findByFollower(Member following);


}
