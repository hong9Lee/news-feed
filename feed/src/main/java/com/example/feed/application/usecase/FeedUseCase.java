package com.example.feed.application.usecase;

import com.example.feed.framework.web.dto.FeedDTO;

public interface FeedUseCase {
    FeedDTO getFeed(Long memberSeq);
}
