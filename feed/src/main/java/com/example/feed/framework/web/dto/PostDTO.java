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
public class PostDTO {

    private long memberSeq;

    private String title;
    private String description;
    private int likeCount;
    private List<PostCommentDTO> postComments;

}
