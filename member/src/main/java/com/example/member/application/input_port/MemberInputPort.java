package com.example.member.application.input_port;

import com.example.member.application.output_port.MemberOutPutPort;
import com.example.member.application.usecase.MemberUseCase;
import com.example.member.domain.model.entity.Member;
import com.example.member.framework.web.dto.MemberJoinInputDTO;
import com.example.member.framework.web.dto.MemberJoinOutPutDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberInputPort implements MemberUseCase {

    private final MemberOutPutPort memberOutPutPort;

    @Override
    public MemberJoinOutPutDTO createNewMember(MemberJoinInputDTO memberJoinInputDTO) {
        Member savedMember = memberOutPutPort.save(Member.builder()
                .name(memberJoinInputDTO.getName())
                .build());
        log.info("createNewMember seq:{}, name:{}", savedMember.getSeq(), savedMember.getName());
        return new MemberJoinOutPutDTO().mapToDTO(savedMember);
    }
}
