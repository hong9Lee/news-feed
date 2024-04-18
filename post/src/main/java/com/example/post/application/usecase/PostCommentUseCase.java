package com.example.post.application.usecase;

import com.example.post.framework.web.dto.post.PostCommentInPutDTO;

public interface PostCommentUseCase {
    void postComment(PostCommentInPutDTO postCommentInPutDTO);
}
