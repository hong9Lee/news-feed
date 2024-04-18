package com.example.post.application.output_port;

import com.example.post.domain.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostOutPutPort {
    Post save(Post post);

    Optional<Post> findById(Long id);
}
