package com.atipera.githubproxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
class GithubClient {
    private final RestClient restClient;

    public GithubClient(@Value("${github.api.url}") String baseUrl){
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    List<GithubRepoDto> getUserRepositories(String username){
        return restClient.get()
                .uri("users/{username}/repos", username)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
    List<GithubBranchDto> getBranches(String owner, String repo) {
        return restClient.get()
                .uri("/repos/{owner}/{repo}/branches", owner, repo)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
