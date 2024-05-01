package com.example.feed.framework.web.controller;

import com.example.feed.application.usecase.FeedUseCase;
import com.example.feed.framework.web.dto.FeedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
@Slf4j
public class FeedController {

    private final FeedUseCase feedUseCase;

    @GetMapping("/feed/{memberSeq}")
    public ResponseEntity<FeedDTO> getFeed(@PathVariable Long memberSeq) {
        log.info("feed controller request init");
        FeedDTO feedDTO = feedUseCase.getFeed(memberSeq);
        return ResponseEntity.ok(feedDTO);
    }

}
