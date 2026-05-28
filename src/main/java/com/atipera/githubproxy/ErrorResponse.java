package com.atipera.githubproxy;

public record ErrorResponse(
        int status,
        String message
) {}
