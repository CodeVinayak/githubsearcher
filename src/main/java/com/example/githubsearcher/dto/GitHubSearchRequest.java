package com.example.githubsearcher.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "Request body for searching GitHub repositories")
public class GitHubSearchRequest {
    @NotBlank(message = "Query cannot be empty")
    @Schema(description = "The search query (e.g., 'spring boot')", example = "spring boot")
    private String query;

    @Schema(description = "Optional: Filter by programming language (e.g., 'Java')", example = "Java")
    private String language;

    @Schema(description = "Optional: Sort by 'stars', 'forks', or 'updated'", example = "stars", allowableValues = {"stars", "forks", "updated"})
    private String sort;
} 