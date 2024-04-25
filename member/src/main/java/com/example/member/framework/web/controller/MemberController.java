package com.example.member.framework.web.controller;

import com.example.member.application.usecase.MemberUseCase;
import com.example.member.application.usecase.elastic.EsFollowUseCase;
import com.example.member.framework.web.dto.member.MemberJoinInPutDTO;
import com.example.member.framework.web.dto.member.MemberJoinOutPutDTO;
import com.example.member.framework.web.dto.member.RelationsOutPutDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberUseCase memberUseCase;
    private final EsFollowUseCase esFollowUseCase;

    @PostMapping("/join")
    public ResponseEntity<MemberJoinOutPutDTO> joinMember(@RequestBody MemberJoinInPutDTO memberJoinInputDTO) {
        log.info("following controller request init");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberUseCase.createNewMember(memberJoinInputDTO));
    }

    @GetMapping("/relation/{memberSeq}")
    public ResponseEntity<RelationsOutPutDTO> getMemberRelations(@PathVariable Long memberSeq) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(esFollowUseCase.getMemberRelations(memberSeq));

    }

}
