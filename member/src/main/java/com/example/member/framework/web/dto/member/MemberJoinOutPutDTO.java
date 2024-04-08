package com.example.member.framework.web.dto.member;

import com.example.member.domain.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MemberJoinOutPutDTO {

    private long seq;

    private String name;

    private String createDateTime;

    public MemberJoinOutPutDTO mapToDTO(Member member) {
        return MemberJoinOutPutDTO.builder()
                .seq(member.getSeq())
                .name(member.getName())
                .createDateTime(member.getCreateDateTime().toString())
                .build();
    }
}
