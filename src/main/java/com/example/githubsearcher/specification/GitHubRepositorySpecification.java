package com.example.githubsearcher.specification;

import com.example.githubsearcher.model.GitHubRepository;
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class GitHubRepositorySpecification {

    public static Specification<GitHubRepository> hasLanguage(String language) {
        return (root, query, criteriaBuilder) ->
                language == null || language.isEmpty() ? null : criteriaBuilder.equal(root.get("language"), language);
    }

    public static Specification<GitHubRepository> hasMinStars(Integer minStars) {
        return (root, query, criteriaBuilder) ->
                minStars == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("stars"), minStars);
    }

    public static Specification<GitHubRepository> orderBy(String sortBy) {
        return (root, query, criteriaBuilder) -> {
            if (sortBy == null || sortBy.isEmpty()) {
                return null; // No sorting applied
            }
            Order order;
            switch (sortBy.toLowerCase()) {
                case "stars":
                    order = criteriaBuilder.desc(root.get("stars"));
                    break;
                case "forks":
                    order = criteriaBuilder.desc(root.get("forks"));
                    break;
                case "updated":
                    order = criteriaBuilder.desc(root.get("lastUpdated"));
                    break;
                default:
                    return null; // No sorting applied for unknown sortBy
            }
            query.orderBy(order);
            return null; // Return null as the order is set on the query directly
        };
    }
} 