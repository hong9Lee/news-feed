package com.example.feed.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long id;

    @Column(name = "post_member_seq")
    private long memberSeq;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_description")
    private String description;

    @Column(name = "post_like_count")
    private int likeCount;

    @OneToMany(mappedBy = "post")
    private List<PostComment> postComment;

}
