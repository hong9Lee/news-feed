package com.example.member.application.usecase;

import com.example.member.framework.web.dto.MemberJoinInputDTO;
import com.example.member.framework.web.dto.MemberJoinOutPutDTO;

public interface MemberUseCase {
    MemberJoinOutPutDTO createNewMember(MemberJoinInputDTO memberJoinInputDTO);
}
