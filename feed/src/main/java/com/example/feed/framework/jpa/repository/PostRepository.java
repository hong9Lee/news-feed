package com.example.feed.framework.jpa.repository;

import com.example.feed.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByIdIn(Set<Long> postSeqs);

}
