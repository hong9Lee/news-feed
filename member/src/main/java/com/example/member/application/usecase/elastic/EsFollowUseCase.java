package com.example.member.application.usecase.elastic;

import com.example.member.framework.web.dto.member.RelationsOutPutDTO;

public interface EsFollowUseCase {
    RelationsOutPutDTO getMemberRelations(Long memberSeq);
}
