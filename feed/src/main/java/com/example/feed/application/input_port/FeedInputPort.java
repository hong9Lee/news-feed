package com.example.feed.application.input_port;

import com.example.feed.application.output_port.PostCommentOutPutPort;
import com.example.feed.application.output_port.PostOutPutPort;
import com.example.feed.application.usecase.FeedUseCase;
import com.example.feed.domain.entity.Post;
import com.example.feed.domain.entity.PostComment;
import com.example.feed.framework.dto.RelationsOutPutDTO;
import com.example.feed.util.MemberUtil;
import com.example.feed.util.WebClientUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

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
    private final PostOutPutPort postOutPutPort;
    private final PostCommentOutPutPort postCommentOutPutPort;


    @Override
    public void getFeed(Long memberSeq) {
//        if (memberUtil.isValidMemberSeq(memberSeq)) {
//            log.error("존재하지 않는 member 입니다. memberSeq:{}", memberSeq);
//            throw new NoSuchElementException("존재하지 않는 member 입니다");
//        }


        // TODO: cache 미스 시, feed 생성
        var relationsOutPutDTO = webClientUtil.makeRestCall("http://localhost:8081/api/v1/member/relation/" + memberSeq,
                HttpMethod.GET, RelationsOutPutDTO.class);
        var postSeqSet = findPostCacheData(relationsOutPutDTO);
        createFeed(postSeqSet);

    }

    private Set<Long> findPostCacheData(RelationsOutPutDTO relationsOutPutDTO) {
        var followings = relationsOutPutDTO.getFollowings();
        var followingSeqsSet = followings.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        // TODO: block member 처리 추가 필요.
        return getAllHashFields(followingSeqsSet);
    }

    public Set<Long> getAllHashFields(Set<String> memberSeqs) {
        // TODO: lettuce를 사용하여 JSON 핸들링 변경
        List<Object> rawData = redisTemplate.opsForHash().multiGet(REDIS_POST_KEY, new HashSet<>(memberSeqs));
        return rawData.stream()
                .map(object -> {
                    String json = object.toString();
                    List<String> list = gson.fromJson(json, new TypeToken<List<String>>() {
                    }.getType());
                    return list;
                })
                .flatMap(List::stream)
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }

    /**
     * 일단 Post, PostComment 모델을 공유하며 db 조회하여 feed 데이터 생성.
     * 개선 방안: post, postComment 데이터를 post 서버에 요청해 Feed 생성 후 캐싱
     * 고민 포인트: post 서버에 요청할 양이 많아진다면 타임아웃이 발생하지 않을까?
     */
    private void createFeed(Set<Long> postSeqSet) {
        List<Post> postEntities = postOutPutPort.findAllByIdIn(postSeqSet);
        List<PostComment> postCommentEntities = postCommentOutPutPort.findAllByPostId(postSeqSet);

        // TODO: post, postComment 조합하여 캐싱.
    }


}
