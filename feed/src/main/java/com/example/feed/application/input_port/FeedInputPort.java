package com.example.feed.application.input_port;

import com.example.feed.application.usecase.FeedUseCase;
import com.example.feed.framework.dto.RelationsOutPutDTO;
import com.example.feed.util.MemberUtil;
import com.example.feed.util.WebClientUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FeedInputPort implements FeedUseCase {

    private final String REDIS_POST_KEY = "postMemberSeqs";
    private final MemberUtil memberUtil;
    private final WebClientUtil webClientUtil;
    private final RedisTemplate redisTemplate;
    private final Gson gson;

    @Override
    public void getFeed(Long memberSeq) {
//        if (memberUtil.isValidMemberSeq(memberSeq)) {
//            log.error("존재하지 않는 member 입니다. memberSeq:{}", memberSeq);
//            throw new NoSuchElementException("존재하지 않는 member 입니다");
//        }

        webClientUtil.requestGetApi("http://localhost:8081/api/v1/member/relation/" + memberSeq, RelationsOutPutDTO.class)
                .flatMap(relationsOutPutDTO -> {
                    return searchPostsInCache(relationsOutPutDTO);
                }).flatMap(postSeqSet -> {
                    return findPosts(postSeqSet);
                });
                /*
                .subscribe(finalFeedResponse -> {
                    processFeedResponse(finalFeedResponse);
                }, error -> {
                    handleError(error);
                });

                 */

    }

    private Mono<?> findPosts(Set<Long> postSeqSet) {
        // TODO: post 데이터를 찾아 dto 생성


        return null;
    }

    private Mono<Set<Long>> searchPostsInCache(RelationsOutPutDTO relationsOutPutDTO) {
        var followings = relationsOutPutDTO.getFollowings();
        var followingSeqsSet = followings.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        return Mono.fromCallable(() -> getAllHashFields(followingSeqsSet))
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(e -> {
                    log.error("Failed to retrieve posts from cache", e);
                    return Mono.just(new HashSet<>());
                });
    }

    public Set<Long> getAllHashFields(Set<String> memberSeqs) {
        List<Object> rawData = redisTemplate.opsForHash().multiGet(REDIS_POST_KEY, new HashSet<>(memberSeqs));
        return rawData.stream()
                .map(object -> {
                    String json = object.toString();
                    List<String> list = gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
                    return list;
                })
                .flatMap(List::stream)
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }


}
