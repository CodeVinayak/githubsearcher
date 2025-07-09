package com.example.githubsearcher.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Response body for a GitHub repository")
public class GitHubRepositoryResponse {
    @Schema(description = "The unique ID of the GitHub repository", example = "123456")
    private Long id;
    @Schema(description = "The name of the repository", example = "spring-boot-example")
    private String name;
    @Schema(description = "A brief description of the repository", example = "An example repository for Spring Boot")
    private String description;
    @Schema(description = "The login name of the repository owner", example = "user123")
    private String owner;
    @Schema(description = "The primary programming language of the repository", example = "Java")
    private String language;
    @Schema(description = "The number of stars the repository has", example = "450")
    private Integer stars;
    @Schema(description = "The number of forks the repository has", example = "120")
    private Integer forks;
    @Schema(description = "The last update timestamp of the repository", example = "2024-01-01T12:00:00Z")
    private LocalDateTime lastUpdated;
} 