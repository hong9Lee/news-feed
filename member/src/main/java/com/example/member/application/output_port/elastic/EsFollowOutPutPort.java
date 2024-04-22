package com.example.member.application.output_port.elastic;

import com.example.member.domain.model.entity.elastic.EsFollow;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EsFollowOutPutPort {
    Optional<EsFollow> findByMemberSeq(long id);
    void save(EsFollow esFollow);
    void saveAll(EsFollow... esFollows);
}
