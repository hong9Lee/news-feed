package com.example.post.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final String REDIS_MEMBER_KEY = "memberSeqs";
    private final RedisTemplate redisTemplate;

    public boolean isValidMemberSeq(Long memberSeq) {
        Boolean isMember = redisTemplate.opsForSet().isMember(REDIS_MEMBER_KEY, String.valueOf(memberSeq));
        return isMember != null && isMember;
    }
}
