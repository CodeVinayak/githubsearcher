package com.example.githubsearcher.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;

@Entity
@Data
public class GitHubRepository {

    @Id
    private Long id;
    private String name;
    private String description;
    private String owner;
    private String language;
    private Integer stars;
    private Integer forks;
    private LocalDateTime lastUpdated;
} 