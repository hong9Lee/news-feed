package com.example.post.framework.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostCommentInPutDTO {

    private long memberSeq;
    private long postSeq;
    private String commentText;

}
