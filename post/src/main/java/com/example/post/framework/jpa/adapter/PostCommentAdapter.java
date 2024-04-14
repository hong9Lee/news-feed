package com.example.post.framework.jpa.adapter;

import com.example.post.application.output_port.PostCommentOutPutPort;
import com.example.post.domain.entity.PostComment;
import com.example.post.framework.jpa.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostCommentAdapter implements PostCommentOutPutPort {

    private final PostCommentRepository postCommentRepository;

    @Override
    public PostComment save(PostComment postComment) {
        return postCommentRepository.save(postComment);
    }
}
