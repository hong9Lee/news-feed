package com.example.feed.framework.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FeedDTO {

    private long memberSeq;
    private List<PostDTO> postDTOS;

}
