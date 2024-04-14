package com.example.post.application.output_port;

import com.example.post.domain.entity.PostComment;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentOutPutPort {
    PostComment save(PostComment postComment);
}
