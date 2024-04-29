package com.example.feed.application.output_port;

import com.example.feed.domain.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostOutPutPort {

    List<Post> findAllByIdIn(Set<Long> postSeqs);


}
