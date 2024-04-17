package com.example.post.framework.web.controller;

import com.example.post.application.usecase.PostUseCase;
import com.example.post.framework.web.dto.post.PostInPutDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostUseCase postUseCase;

    @PostMapping("/posting")
    public ResponseEntity posting(@RequestBody PostInPutDTO postInPutDTO) {
        log.info("posting controller request init");
        postUseCase.posting(postInPutDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
