package com.example.feed.application.output_port;

import com.example.feed.domain.entity.PostComment;

import java.util.List;
import java.util.Set;

public interface PostCommentOutPutPort {
    List<PostComment> findAllByPostId(Set<Long> postSeqs);
}
