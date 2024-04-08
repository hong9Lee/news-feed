package com.example.member.framework.web.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class FollowInPutDTO {

    private long followerMemberSeq;
    private long followingMemberSeq;

}
