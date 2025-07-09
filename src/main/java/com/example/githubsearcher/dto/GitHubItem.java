package com.example.githubsearcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Represents a single repository item from GitHub API response")
public class GitHubItem {
    @Schema(description = "Unique identifier of the repository on GitHub", example = "123456")
    private Long id;

    @Schema(description = "Name of the repository", example = "spring-boot-starter")
    private String name;

    @Schema(description = "Description of the repository", example = "Spring Boot Starter project template.")
    private String description;

    @Schema(description = "Details about the repository owner")
    private GitHubOwner owner;

    @Schema(description = "Primary programming language of the repository", example = "Java")
    private String language;

    @JsonProperty("stargazers_count")
    @Schema(description = "Number of stargazers (stars) for the repository", example = "1500")
    private Integer stars;

    @JsonProperty("forks_count")
    @Schema(description = "Number of forks for the repository", example = "300")
    private Integer forks;

    @JsonProperty("updated_at")
    @Schema(description = "Last update timestamp of the repository", example = "2024-05-20T10:30:00Z")
    private LocalDateTime lastUpdated;

    @Data
    @Schema(description = "Represents the owner of a GitHub repository")
    public static class GitHubOwner {
        @Schema(description = "Login username of the repository owner", example = "octocat")
        private String login;
    }
}
