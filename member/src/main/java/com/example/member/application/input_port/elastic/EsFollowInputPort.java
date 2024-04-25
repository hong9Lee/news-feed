package com.example.member.application.input_port.elastic;

import com.example.member.application.output_port.elastic.EsFollowOutPutPort;
import com.example.member.application.usecase.elastic.EsFollowUseCase;
import com.example.member.framework.web.dto.member.RelationsOutPutDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EsFollowInputPort implements EsFollowUseCase {

    private final EsFollowOutPutPort esFollowOutPutPort;

    @Override
    public RelationsOutPutDTO getMemberRelations(Long memberSeq) {
        var esFollow = esFollowOutPutPort.findByMemberSeq(memberSeq)
                .orElse(null);
        return RelationsOutPutDTO.fromEntity(esFollow);

    }
}
