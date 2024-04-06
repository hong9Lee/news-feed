package com.example.member.domain.model.entity;

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
@Table(name = "follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_seq")
    private long seq;

    @ManyToOne
    @JoinColumn(name = "following_member_id")
    private Member following;

    @ManyToOne
    @JoinColumn(name = "follower_member_id")
    private Member follower;

}
