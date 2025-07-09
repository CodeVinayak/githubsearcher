package com.example.githubsearcher.service;

import com.example.githubsearcher.dto.GitHubApiResponse;
import com.example.githubsearcher.model.GitHubRepository;
import com.example.githubsearcher.repository.GitHubRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubApiService {

    private final WebClient webClient;
    private final GitHubRepositoryRepository gitHubRepositoryRepository;

    @Value("${github.api.url}")
    private String githubApiUrl;

    public Mono<List<GitHubRepository>> searchRepositories(String q, String language, String sort) {
        StringBuilder urlBuilder = new StringBuilder(githubApiUrl + "/search/repositories?q=" + q);
        if (language != null && !language.isEmpty()) {
            urlBuilder.append("+language:").append(language);
        }
        if (sort != null && !sort.isEmpty()) {
            urlBuilder.append("&sort=").append(sort);
        }

        return webClient.get()
                .uri(urlBuilder.toString())
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("GitHub API error: " + clientResponse.statusCode())))
                .bodyToMono(GitHubApiResponse.class)
                .map(response -> response.getItems().stream()
                        .map(item -> {
                            GitHubRepository repo = new GitHubRepository();
                            repo.setId(item.getId());
                            repo.setName(item.getName());
                            repo.setDescription(item.getDescription());
                            repo.setOwner(item.getOwner().getLogin());
                            repo.setStars(item.getStars());
                            repo.setForks(item.getForks());
                            repo.setLanguage(item.getLanguage());
                            repo.setLastUpdated(item.getLastUpdated()); // ✅ Fixed here
                            return repo;
                        })
                        .collect(Collectors.toList()))
                .flatMap(repos -> Mono.fromCallable(() -> {
                    gitHubRepositoryRepository.saveAll(repos);
                    return repos;
                }));
    }

    public Page<GitHubRepository> getStoredRepositories(Specification<GitHubRepository> spec, Pageable pageable) {
        return gitHubRepositoryRepository.findAll(spec, pageable);
    }
}
