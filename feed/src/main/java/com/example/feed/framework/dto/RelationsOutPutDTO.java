package com.example.feed.framework.dto;

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

}
