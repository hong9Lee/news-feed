package com.example.member.framework.web.dto.member;

import com.example.member.domain.model.entity.elastic.EsFollow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RelationsOutPutDTO {

    private long memberSeq;
    private List<Long> followers;
    private List<Long> followings;
    private List<Long> blockMembers;

    public static RelationsOutPutDTO fromEntity(EsFollow esFollow) {
        return RelationsOutPutDTO.builder()
                .memberSeq(esFollow.getMemberSeq())
                .followers(esFollow.getFollowers())
                .followings(esFollow.getFollowings())
                .blockMembers(esFollow.getBlockMembers())
                .build();
    }
}
