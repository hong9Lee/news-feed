package com.example.member.framework.jpa.adapter;

import com.example.member.application.output_port.elastic.EsFollowOutPutPort;
import com.example.member.domain.model.entity.elastic.EsFollow;
import com.example.member.framework.jpa.repository.elastic.EsFollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EsFollowAdapter implements EsFollowOutPutPort {

    private final EsFollowRepository esFollowRepository;

    @Override
    public Optional<EsFollow> findByMemberSeq(long id) {
        return esFollowRepository.findAllByMemberSeq(id);
    }

    @Override
    public void save(EsFollow esFollow) {
        esFollowRepository.save(esFollow);
    }

    @Override
    public void saveAll(EsFollow... esFollows) {
        esFollowRepository.saveAll(Arrays.asList(esFollows));
    }
}
