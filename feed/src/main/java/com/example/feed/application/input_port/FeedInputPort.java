package com.example.feed.application.input_port;

import com.example.feed.application.output_port.PostCommentOutPutPort;
import com.example.feed.application.output_port.PostOutPutPort;
import com.example.feed.application.usecase.FeedUseCase;
import com.example.feed.domain.entity.Post;
import com.example.feed.domain.entity.PostComment;
import com.example.feed.framework.dto.RelationsOutPutDTO;
import com.example.feed.framework.web.dto.FeedDTO;
import com.example.feed.framework.web.dto.PostCommentDTO;
import com.example.feed.framework.web.dto.PostDTO;
import com.example.feed.util.GrpcClientUtil;
import com.example.feed.util.MemberUtil;
import com.example.feed.util.WebClientUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.lettuce.core.api.sync.RedisCommands;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import member.MemberRelation;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    private final String REDIS_FEED_KEY = "feedData";
    private final MemberUtil memberUtil;
    private final WebClientUtil webClientUtil;
    private final RedisTemplate redisTemplate;
    private final Gson gson;
    private final PostOutPutPort postOutPutPort;
    private final GrpcClientUtil grpcClientUtil;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public FeedDTO getFeed(Long memberSeq) {
//        if (memberUtil.isValidMemberSeq(memberSeq)) {
//            log.error("존재하지 않는 member 입니다. memberSeq:{}", memberSeq);
//            throw new NoSuchElementException("존재하지 않는 member 입니다");
//        }

        /** 캐시 히트시 dto feed 데이터 반환 */
//        var feedDTO = fetchFeedDTO(memberSeq);
//        if(feedDTO != null) {
//            return feedDTO;
//        }

        /** member 서버에 요청하여 els에 저장되어있는 회원의 팔로워 정보를 가져온다.
         * TODO: rest 통신에서 rpc 통신으로 개선하여 성능 비교해보기. */

        /**
         * 1. rest 통신
         */
//        var relationsOutPutDTO = webClientUtil.makeRestCall("http://localhost:8081/api/v1/member/relation/" + memberSeq,
//                HttpMethod.GET, RelationsOutPutDTO.class);
//        var postSeqSet = findPostCacheData(relationsOutPutDTO);

        /**
         * 2. rpc 통신
         */
        var postSeqSet = findPostCacheData(convertToDto(grpcClientUtil.getMemberRelations(memberSeq)));
        return createFeed(postSeqSet, memberSeq);
    }

    public RelationsOutPutDTO convertToDto(MemberRelation.MemberResponse response) {
        List<Long> followers = response.getFollowersList();
        List<Long> followings = response.getFollowingsList();
        List<Long> blockMembers = response.getBlockMembersList();

        return RelationsOutPutDTO.builder()
                .memberSeq(response.getMemberSeq())
                .followers(followers)
                .followings(followings)
                .blockMembers(blockMembers)
                .build();
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
        List<String> rawData = redisTemplate.opsForHash().multiGet(REDIS_POST_KEY, new HashSet<>(memberSeqs));
        return rawData.stream()
                .map(e -> convertJsonToList(e))
                .flatMap(List::stream)
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }


    /**
     * 일단 Post, PostComment 모델을 공유하며 db 조회하여 feed 데이터 생성.
     * 개선 방안: post, postComment 데이터를 post 서버에 요청해 Feed 생성 후 캐싱
     * 고민 포인트: post 서버에 요청할 양이 많아진다면 타임아웃이 발생하지 않을까?
     */
    private FeedDTO createFeed(Set<Long> postSeqSet, Long memberSeq) {
        var postEntities = postOutPutPort.findAllByIdIn(postSeqSet);

        List<PostDTO> postDTOS = new ArrayList<>();
        postEntities.forEach(postEntity -> {
            var commentDTOS = PostCommentDTO.convertCommentEntities(postEntity);
            postDTOS.add(PostDTO.builder()
                    .memberSeq(postEntity.getMemberSeq())
                    .title(postEntity.getTitle())
                    .description(postEntity.getDescription())
                    .likeCount(postEntity.getLikeCount())
                    .postComments(commentDTOS)
                    .build());
        });

        FeedDTO feedDTO = FeedDTO.builder()
                .memberSeq(memberSeq)
                .postDTOS(postDTOS)
                .build();

        // post, postComment 조합하여 캐싱.
        try {
            String feedJson = objectMapper.writeValueAsString(feedDTO);
            HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
            hashOps.put(REDIS_FEED_KEY, memberSeq.toString(), feedJson);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return feedDTO;
    }

    public FeedDTO fetchFeedDTO(Long memberSeq) {
        HashOperations<String, Long, String> hashOps = redisTemplate.opsForHash();
        String feedJson = hashOps.get(REDIS_FEED_KEY, memberSeq.toString());
        if (feedJson != null) {
            try {
                return objectMapper.readValue(feedJson, FeedDTO.class);
            } catch (JsonProcessingException e) {
                log.error("Error deserializing FeedDTO: {}", e.getMessage(), e);
            }
        }
        return null;
    }


    private List<String> convertJsonToList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

}
