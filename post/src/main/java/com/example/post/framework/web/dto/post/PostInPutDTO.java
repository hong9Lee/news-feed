package com.example.post.framework.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostInPutDTO {

    private long memberSeq;
    private String title;
    private String description;

}
