package com.example.feed.framework.jpa.adapter;

import com.example.feed.application.output_port.PostOutPutPort;
import com.example.feed.domain.entity.Post;
import com.example.feed.framework.jpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class PostAdapter implements PostOutPutPort {

    private final PostRepository postRepository;


    @Override
    public List<Post> findAllByIdIn(Set<Long> postSeqs) {
        return postRepository.findAllByIdIn(postSeqs);
    }
}
