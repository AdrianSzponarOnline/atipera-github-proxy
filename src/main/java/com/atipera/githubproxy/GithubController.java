package com.atipera.githubproxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {
    private final GithubService githubService;

    @Autowired
    GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/users/{username}/repos")
    public List<RepoResponse> getUserRepositories(@PathVariable String username) {
        return githubService.getNonForkRepositories(username);
    }
}
