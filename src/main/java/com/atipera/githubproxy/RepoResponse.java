package com.atipera.githubproxy;

import java.util.List;

public record RepoResponse(
        String repositoryName,
        String ownerLogin,
        List<BranchResponse> branches
) {}

