package com.example.member.domain.model.entity.elastic;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Document(indexName = "follow", createIndex = true)
public class EsFollow {

    @Id
    private String id;
    private long memberSeq;
    private List<Long> followers;
    private List<Long> followings;
    private List<Long> blockMembers;

    public void addFollowers(Long... followerSeq) {
        if(CollectionUtils.isEmpty(this.followers)) {
            this.followers = new ArrayList<>();
        }

        Collections.addAll(this.followers, followerSeq);
    }

    public void addFollowings(Long... followingSeq) {
        if(CollectionUtils.isEmpty(this.followings)) {
            this.followings = new ArrayList<>();
        }

        Collections.addAll(this.followings, followingSeq);
    }

    public void addBlockMembers(Long... blockMemberSeq) {
        if(CollectionUtils.isEmpty(this.blockMembers)) {
            this.blockMembers = new ArrayList<>();
        }

        Collections.addAll(this.blockMembers, blockMemberSeq);
    }
}
