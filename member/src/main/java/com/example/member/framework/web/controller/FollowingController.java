package com.example.member.framework.web.controller;

import com.example.member.application.usecase.FollowUseCase;
import com.example.member.framework.web.dto.follow.FollowInPutDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@Slf4j
public class FollowingController {

    private final FollowUseCase followUseCase;

    @PostMapping("/following")
    public ResponseEntity following(@RequestBody FollowInPutDTO followInPutDTO) {
        log.info("following controller request init");
        followUseCase.following(followInPutDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
