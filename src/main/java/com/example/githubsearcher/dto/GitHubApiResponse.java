package com.example.githubsearcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GitHubApiResponse {
    @JsonProperty("total_count")
    private int totalCount;
    private boolean incompleteResults;
    private List<GitHubItem> items;
} 