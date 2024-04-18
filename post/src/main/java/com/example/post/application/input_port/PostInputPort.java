package com.example.post.application.input_port;

import com.example.post.application.output_port.PostOutPutPort;
import com.example.post.application.usecase.PostUseCase;
import com.example.post.domain.entity.Post;
import com.example.post.framework.web.dto.post.PostInPutDTO;
import com.example.post.util.MemberUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostInputPort implements PostUseCase {

    private final PostOutPutPort postOutPutPort;
    private final MemberUtil memberUtil;

    @Override
    public void posting(PostInPutDTO postInPutDTO) {
        /**
         * 1. 직접 요청하기: Member 서버에 직접 요청을 보내어 memberSeq의 유효성을 확인.
         * 이 방법은 구현이 간단하고 실시간으로 정확한 데이터를 확인할 수 있다는 장점이 있다.
         * 하지만, Member 서버에 장애가 발생하면 Post 서버도 영향을 받을 수 있으며, 네트워크 지연으로 인한 성능 문제가 발생할 수 있다.
         *
         * 2. 캐싱: Member 서버의 memberSeq 정보를 Post 서버에 일정 기간 동안 캐싱해 놓는다. 캐싱은 네트워크 지연을 줄이고 Member 서버의 부하를 감소시킬 수 있는 방법이다.
         * 그러나 캐시의 데이터가 최신 상태가 아닐 수 있기 때문에 데이터 일관성 문제가 발생할 수 있다.
         *
         * 3. 이벤트 기반 업데이트: Member 서버에서 회원 정보에 변경이 발생할 때마다 이벤트를 발생시키고, Post 서버가 이 이벤트를 구독하여 db 또는 캐시를 업데이트할 수 있다.
         * 데이터의 실시간 업데이트를 보장하며 시스템의 결합도를 낮출 수 있다.
         * */
        if (memberUtil.isValidMemberSeq(postInPutDTO.getMemberSeq())) {
            log.error("존재하지 않는 member 입니다. memberSeq:{}", postInPutDTO.getMemberSeq());
            throw new NoSuchElementException("존재하지 않는 member 입니다");
        }

        Post post = Post.builder()
                .memberSeq(postInPutDTO.getMemberSeq())
                .title(postInPutDTO.getTitle())
                .description(postInPutDTO.getDescription())
                .build();

        postOutPutPort.save(post);
    }
}
