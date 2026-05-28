package com.atipera.githubproxy;

record GithubRepoDto(
        String name,
        GithubOwnerDto owner,
        boolean fork
) { }


record GithubOwnerDto(
        String login
){}

record GithubBranchDto(
        String name,
        GithubCommitDto commit
){}

record GithubCommitDto(
        String sha
){}