package com.example.githubsearcher.controller;

import com.example.githubsearcher.dto.GitHubRepositoryResponse;
import com.example.githubsearcher.dto.GitHubSearchRequest;
import com.example.githubsearcher.mapper.GitHubRepositoryMapper;
import com.example.githubsearcher.model.GitHubRepository;
import com.example.githubsearcher.service.GitHubApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.springframework.data.jpa.domain.Specification.where;
import static com.example.githubsearcher.specification.GitHubRepositorySpecification.hasLanguage;
import static com.example.githubsearcher.specification.GitHubRepositorySpecification.hasMinStars;
import static com.example.githubsearcher.specification.GitHubRepositorySpecification.orderBy;

@RestController
@RequestMapping("/api/github")
@Tag(name = "GitHub Repository Management", description = "APIs for searching and managing GitHub repositories")
public class GitHubController {

    private final GitHubApiService gitHubApiService;
    private final GitHubRepositoryMapper mapper;

    @Autowired
    public GitHubController(GitHubApiService gitHubApiService, GitHubRepositoryMapper mapper) {
        this.gitHubApiService = gitHubApiService;
        this.mapper = mapper;
    }

    @PostMapping("/search")
    @Operation(summary = "Search GitHub repositories and save to database",
            description = "Fetches repositories from GitHub API based on query, language, and sort criteria, then saves/updates them in the local database.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Repositories fetched and saved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GitHubRepositoryResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input parameters"),
                    @ApiResponse(responseCode = "429", description = "GitHub API rate limit exceeded"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public Mono<ResponseEntity<Map<String, Object>>> searchRepositories(@Valid @RequestBody GitHubSearchRequest request) {
        return gitHubApiService.searchRepositories(
                request.getQuery(), request.getLanguage(), request.getSort())
                .map(repositories -> {
                    List<GitHubRepositoryResponse> responseDtos = mapper.toResponseDtoList(repositories);
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Repositories fetched and saved successfully");
                    response.put("repositories", responseDtos);
                    return ResponseEntity.ok(response);
                });
    }

    @GetMapping("/repositories")
    @Operation(summary = "Retrieve stored GitHub repositories",
            description = "Retrieves repository details from the local database based on optional filters (language, minimum stars) and sorting criteria.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved repositories",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GitHubRepositoryResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<List<GitHubRepositoryResponse>> getStoredRepositories(
            @Parameter(description = "Filter repositories by programming language") @RequestParam(required = false) String language,
            @Parameter(description = "Filter repositories by minimum stars count") @RequestParam(required = false) Integer minStars,
            @Parameter(description = "Sort repositories by 'stars', 'forks', or 'updated'") @RequestParam(required = false) String sort,
            @Parameter(description = "Page number (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size) {

        Specification<GitHubRepository> spec = where(hasLanguage(language))
                .and(hasMinStars(minStars))
                .and(orderBy(sort));

        Pageable pageable = PageRequest.of(page, size);

        Page<GitHubRepository> repositories = gitHubApiService.getStoredRepositories(spec, pageable);
        List<GitHubRepositoryResponse> responseDtos = mapper.toResponseDtoList(repositories.getContent());

        return ResponseEntity.ok(responseDtos);
    }
} 