package com.example.post.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id")
    private long id;

    @Column(name = "post_member_seq")
    private long memberSeq;

    @Column(name = "post_comment_text")
    private String commentText;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
