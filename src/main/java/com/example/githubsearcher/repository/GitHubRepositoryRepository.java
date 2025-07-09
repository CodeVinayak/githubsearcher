package com.example.githubsearcher.repository;

import com.example.githubsearcher.model.GitHubRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GitHubRepositoryRepository extends JpaRepository<GitHubRepository, Long>, JpaSpecificationExecutor<GitHubRepository> {

    List<GitHubRepository> findByLanguage(String language);

    List<GitHubRepository> findByStarsGreaterThanEqual(Integer minStars);

    List<GitHubRepository> findByLanguageAndStarsGreaterThanEqual(String language, Integer minStars);

    List<GitHubRepository> findAllByOrderByStarsDesc();

    List<GitHubRepository> findAllByOrderByForksDesc();

    List<GitHubRepository> findAllByOrderByLastUpdatedDesc();
} 