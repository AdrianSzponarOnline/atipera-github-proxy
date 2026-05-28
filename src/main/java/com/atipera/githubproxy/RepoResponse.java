package com.atipera.githubproxy;

import java.util.List;

record RepoResponse(
        String repositoryName,
        String ownerLogin,
        List<BranchResponse> branches
) {}

record BranchResponse(
        String branchName,
        String lastCommitSha
){}


