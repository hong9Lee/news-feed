package com.example.feed.framework.jpa.adapter;

import com.example.feed.application.output_port.PostCommentOutPutPort;
import com.example.feed.domain.entity.PostComment;
import com.example.feed.framework.jpa.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class PostCommentAdapter implements PostCommentOutPutPort {

    private final PostCommentRepository postCommentRepository;

    @Override
    public List<PostComment> findAllByPostId(Set<Long> postSeqs) {
        return postCommentRepository.findAllByPostIdIn(postSeqs);
    }
}
