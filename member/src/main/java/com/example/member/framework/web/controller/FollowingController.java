package com.example.member.framework.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
@Slf4j
public class FollowingController {

    @PostMapping("/following")
    public void following() {
        log.info("following controller request init");


    }
}
