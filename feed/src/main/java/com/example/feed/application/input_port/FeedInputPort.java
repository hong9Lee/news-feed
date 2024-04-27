package com.example.feed.application.input_port;

import com.example.feed.application.usecase.FeedUseCase;
import com.example.feed.framework.dto.RelationsOutPutDTO;
import com.example.feed.util.MemberUtil;
import com.example.feed.util.WebClientUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FeedInputPort implements FeedUseCase {

    private final MemberUtil memberUtil;
    private final WebClientUtil webClientUtil;

    @Override
    public void getFeed(Long memberSeq) {
//        if (memberUtil.isValidMemberSeq(memberSeq)) {
//            log.error("존재하지 않는 member 입니다. memberSeq:{}", memberSeq);
//            throw new NoSuchElementException("존재하지 않는 member 입니다");
//        }


        webClientUtil.requestGetApi("http://localhost:8081/api/v1/member/relation/" + memberSeq, RelationsOutPutDTO.class)
                .flatMap(relationsOutPutDTO -> {
                    return searchPostsInCache(relationsOutPutDTO);
                });
                /*
                .flatMap(postsInfo -> {
                    return combinePostDetails(postsInfo);
                })
                .subscribe(finalFeedResponse -> {
                    processFeedResponse(finalFeedResponse);
                }, error -> {
                    handleError(error);
                });

                 */

    }

    private Mono<?> searchPostsInCache(RelationsOutPutDTO relationsOutPutDTO) {


        return null;
    }


}
