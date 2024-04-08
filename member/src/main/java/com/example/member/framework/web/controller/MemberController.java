package com.example.member.framework.web.controller;

import com.example.member.application.usecase.MemberUseCase;
import com.example.member.framework.web.dto.member.MemberJoinInputDTO;
import com.example.member.framework.web.dto.member.MemberJoinOutPutDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberUseCase memberUseCase;

    @PostMapping("/join")
    public ResponseEntity<MemberJoinOutPutDTO> joinMember(@RequestBody MemberJoinInputDTO memberJoinInputDTO) {
        log.info("following controller request init");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberUseCase.createNewMember(memberJoinInputDTO));
    }
}
