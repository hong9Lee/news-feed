package com.example.member.application.input_port;

import com.example.member.application.output_port.MemberOutPutPort;
import com.example.member.application.usecase.MemberUseCase;
import com.example.member.domain.model.entity.Member;
import com.example.member.framework.web.dto.member.MemberJoinInputDTO;
import com.example.member.framework.web.dto.member.MemberJoinOutPutDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberInputPort implements MemberUseCase {

    private final String REDIS_MEMBER_KEY = "memberSeqs";
    private final MemberOutPutPort memberOutPutPort;
    private final RedisTemplate redisTemplate;

    @Override
    public MemberJoinOutPutDTO createNewMember(MemberJoinInputDTO memberJoinInputDTO) {
        Member savedMember = memberOutPutPort.save(Member.builder()
                .name(memberJoinInputDTO.getName())
                .build());
        log.info("createNewMember seq:{}, name:{}", savedMember.getSeq(), savedMember.getName());

        addMemberSeqToSet(savedMember.getSeq());
        return new MemberJoinOutPutDTO().mapToDTO(savedMember);
    }

    public void addMemberSeqToSet(long memberSeq) {
        redisTemplate.opsForSet().add(REDIS_MEMBER_KEY, String.valueOf(memberSeq));
        log.info("[redis] addMemberSeqToSet memberSeqs memberSeq:{}", memberSeq);
    }
}
