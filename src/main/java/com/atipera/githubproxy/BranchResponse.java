package com.atipera.githubproxy;

public record BranchResponse(
        String branchName,
        String lastCommitSha
){}
