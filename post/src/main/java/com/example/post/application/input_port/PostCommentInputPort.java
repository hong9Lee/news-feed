package com.example.post.application.input_port;

import com.example.post.application.output_port.PostCommentOutPutPort;
import com.example.post.application.output_port.PostOutPutPort;
import com.example.post.application.usecase.PostCommentUseCase;
import com.example.post.domain.entity.Post;
import com.example.post.domain.entity.PostComment;
import com.example.post.framework.web.dto.post.PostCommentInPutDTO;
import com.example.post.util.MemberUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostCommentInputPort implements PostCommentUseCase {

    private final MemberUtil memberUtil;
    private final PostOutPutPort postOutPutPort;
    private final PostCommentOutPutPort postCommentOutPutPort;

    @Override
    public void postComment(PostCommentInPutDTO postCommentInPutDTO) {
        if (!memberUtil.isValidMemberSeq(postCommentInPutDTO.getMemberSeq())) {
            log.error("존재하지 않는 member 입니다. memberSeq:{}", postCommentInPutDTO.getMemberSeq());
            throw new NoSuchElementException("존재하지 않는 member 입니다");
        }

        var post = postOutPutPort.findById(postCommentInPutDTO.getPostSeq())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 post seq 입니다."));

        postCommentOutPutPort.save(
                PostComment.builder()
                .memberSeq(postCommentInPutDTO.getMemberSeq())
                .commentText(postCommentInPutDTO.getCommentText())
                .post(post)
                .build());
    }
}
