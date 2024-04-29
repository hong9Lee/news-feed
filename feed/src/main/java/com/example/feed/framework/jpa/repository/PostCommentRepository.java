package com.example.feed.framework.jpa.repository;

import com.example.feed.domain.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findAllByPostIdIn(Set<Long> postSeqs);
}
