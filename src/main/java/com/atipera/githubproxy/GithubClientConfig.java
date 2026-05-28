package com.atipera.githubproxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class GithubClientConfig {
    @Bean
    RestClient restClient(@Value("${github.api.url}") String githubUrl) {
        return RestClient.builder()
                .baseUrl(githubUrl)
                .build();
    }
}
