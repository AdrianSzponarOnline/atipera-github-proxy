package com.atipera.githubproxy;

record ErrorResponse(
        int status,
        String message
) {}
