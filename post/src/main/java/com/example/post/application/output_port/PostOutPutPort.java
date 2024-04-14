package com.example.post.application.output_port;

import com.example.post.domain.entity.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostOutPutPort {
    Post save(Post post);
}
