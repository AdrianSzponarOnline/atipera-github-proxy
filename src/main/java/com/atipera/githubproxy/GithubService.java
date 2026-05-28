package com.atipera.githubproxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class GithubService
{
    private final GithubClient githubClient;

    @Autowired
    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    List<RepoResponse> getNonForkRepositories(String username){
        return githubClient.getUserRepositories(username).stream()
                .filter(repo -> !repo.fork())
                .map(this::mapToRepoResponse)
                .toList();
    }
    private RepoResponse mapToRepoResponse(GithubRepoDto repoDto){
        List<BranchResponse> branches = githubClient.getBranches(repoDto.owner().login(), repoDto.name())
                .stream()
                .map(branch -> new BranchResponse(
                        branch.name(),
                        branch.commit().sha()))
                .toList();
        return new RepoResponse(repoDto.name(), repoDto.owner().login(), branches);
    }
}
