package com.example.member.framework.jpa.repository.elastic;

import com.example.member.domain.model.entity.elastic.EsFollow;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface EsFollowRepository extends ElasticsearchRepository<EsFollow, Long> {
    Optional<EsFollow> findAllByMemberSeq(long memberSeq);
}
