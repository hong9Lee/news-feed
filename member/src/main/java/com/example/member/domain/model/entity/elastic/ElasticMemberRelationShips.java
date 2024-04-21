package com.example.member.domain.model.entity.elastic;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.List;

@AllArgsConstructor
@Builder
@Document(indexName = "member_relationships", createIndex = true)
public class ElasticMemberRelationShips {

    @Id
    private String id;
    private long memberSeq;
    private List<Long> followers;
    private List<Long> followings;
    private List<Long> blockMembers;
}
