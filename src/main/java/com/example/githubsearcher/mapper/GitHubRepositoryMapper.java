package com.example.githubsearcher.mapper;

import com.example.githubsearcher.dto.GitHubRepositoryResponse;
import com.example.githubsearcher.model.GitHubRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GitHubRepositoryMapper {

    public GitHubRepositoryResponse toResponseDto(GitHubRepository repository) {
        if (repository == null) {
            return null;
        }
        GitHubRepositoryResponse dto = new GitHubRepositoryResponse();
        dto.setId(repository.getId());
        dto.setName(repository.getName());
        dto.setDescription(repository.getDescription());
        dto.setOwner(repository.getOwner());
        dto.setLanguage(repository.getLanguage());
        dto.setStars(repository.getStars());
        dto.setForks(repository.getForks());
        dto.setLastUpdated(repository.getLastUpdated());
        return dto;
    }

    public List<GitHubRepositoryResponse> toResponseDtoList(List<GitHubRepository> repositories) {
        return repositories.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
} 