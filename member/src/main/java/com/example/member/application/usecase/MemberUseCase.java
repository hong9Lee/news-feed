package com.example.member.application.usecase;

import com.example.member.framework.web.dto.member.MemberJoinInputDTO;
import com.example.member.framework.web.dto.member.MemberJoinOutPutDTO;

public interface MemberUseCase {
    MemberJoinOutPutDTO createNewMember(MemberJoinInputDTO memberJoinInputDTO);
}
