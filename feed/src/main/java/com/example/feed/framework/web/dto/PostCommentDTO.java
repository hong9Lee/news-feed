package com.example.feed.framework.web.dto;

import com.example.feed.domain.entity.Post;
import com.example.feed.domain.entity.PostComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostCommentDTO {
    private long memberSeq;
    private String commentText;

    public static List<PostCommentDTO> convertCommentEntities(Post postEntity) {
        var postComments = postEntity.getPostComment();
        return postComments.stream()
                .map(postComment -> PostCommentDTO.builder()
                        .memberSeq(postComment.getMemberSeq())
                        .commentText(postComment.getCommentText())
                        .build())
                .collect(Collectors.toList());
    }
}
