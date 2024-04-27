package com.example.member.application.usecase;

import com.example.member.framework.web.dto.follow.FollowInPutDTO;

public interface FollowUseCase {
    void following(FollowInPutDTO followInPutDTO);
}
