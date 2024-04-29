package com.example.feed.util;

import com.example.feed.config.HttpHeaderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClient webClient;
    private final RestTemplate restTemplate;

    public <T> Mono<T> requestGetApi(String uri, Class<T> responseType) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> T makeRestCall(String url, HttpMethod method, Class<T> responseType) {
        HttpEntity<Object> entity = new HttpEntity<>(HttpHeaderConfig.createHeaders());
        ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);
        return response.getBody();
    }

}
