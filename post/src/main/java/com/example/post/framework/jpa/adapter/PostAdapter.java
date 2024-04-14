package com.example.post.framework.jpa.adapter;

import com.example.post.application.output_port.PostOutPutPort;
import com.example.post.domain.entity.Post;
import com.example.post.framework.jpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostAdapter implements PostOutPutPort {

    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }
}
