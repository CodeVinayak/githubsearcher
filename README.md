# GitHub Repository Searcher

This Spring Boot application allows users to search for GitHub repositories using the GitHub REST API, store search results in a PostgreSQL database, and provide an API endpoint to retrieve stored results based on filter criteria.

## Features
- **GitHub Repository Search:** Fetches and saves GitHub repository details based on query, language, and sort criteria.
- **Store & Retrieve Results:** Stores results in PostgreSQL and allows retrieval with optional filters (language, minStars) and sorting (stars, forks, updated).
- **Efficient Upsert Logic:** Updates existing records or inserts new ones to avoid duplication.
- **Robust Error Handling:** Manages invalid API responses, rate limits, and validation errors.
- **API Documentation:** Integrated with OpenAPI/Swagger UI for easy API exploration.

## Technologies Used
- Java 21, Spring Boot (3.x.x), Maven
- Spring Web, Spring Data JPA, PostgreSQL
- Lombok, Spring Boot DevTools, Spring Boot Validation
- JUnit 5 & Mockito, springdoc-openapi
- WebClient for non-blocking HTTP requests

## Prerequisites
- JDK 17 or higher
- Maven
- PostgreSQL database server

## Getting Started

### 1. Database Setup
Create a PostgreSQL database (e.g., `githubdb`). Update `githubsearcher/src/main/resources/application.properties` with your database credentials.

### 2. Build and Run
Navigate to the `githubsearcher` directory and run:
```bash
cd githubsearcher
mvn clean install
mvn spring-boot:run
```

The application will start on port `8080`.

## API Endpoints

### 1. Search GitHub Repositories
- **Endpoint:** `POST /api/github/search`
- **Description:** Fetches and saves/updates repositories from GitHub.
- **Request Body Example:**
  ```json
  {
    "query": "spring boot",
    "language": "Java",
    "sort": "stars"
  }
  ```
- **Success Response Example (200 OK):**
  ```json
  {
    "message": "Repositories fetched and saved successfully",
    "repositories": [
      {
        "id": 123456,
        "name": "spring-boot-example",
        "description": "An example repository for Spring Boot",
        "owner": "user123",
        "language": "Java",
        "stars": 450,
        "forks": 120,
        "lastUpdated": "2024-01-01T12:00:00Z"
      }
    ]
  }
  ```

### 2. Retrieve Stored Results
- **Endpoint:** `GET /api/github/repositories`
- **Description:** Retrieves stored repository details with optional filters and sorting.
- **Query Parameters:** `language` (optional), `minStars` (optional), `sort` (optional: `stars`, `forks`, `updated`).
- **Example Request:**
  ```
  GET /api/github/repositories?language=Java&minStars=100&sort=stars
  ```
- **Success Response Example (200 OK):**
  ```json
  {
    "repositories": [
      {
        "id": 123456,
        "name": "spring-boot-example",
        "description": "An example repository for Spring Boot",
        "owner": "user123",
        "language": "Java",
        "stars": 450,
        "forks": 120,
        "lastUpdated": "2024-01-01T12:00:00Z"
      }
    ]
  }
  ```

## API Documentation
API documentation is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) once the application is running.

## Testing
Unit and integration tests are in `src/test/java`. Run all tests using `mvn test`.

## Changelog

### 2024-07-10
- Updated `GitHubApiService` for `WebClient` usage, `sort` parameter, and full repository mapping. Added basic error handling.
- Modified `GitHubRepositorySpecification` for dynamic sorting by `stars`, `forks`, or `lastUpdated`.
- Updated `GitHubController` to pass `sort` for API searches and use new sorting specifications for stored results.

### 2024-07-09
- Initial project setup with Maven and Java 17.
- Configured PostgreSQL, created `GitHubRepository` entity, and `GitHubRepositoryRepository`.
- Developed DTOs (`GitHubApiResponse`, `GitHubItem`, `GitHubSearchRequest`, `GitHubRepositoryResponse`) and `GitHubRepositoryMapper`.
- Implemented `GitHubApiService` for API interaction, upsert, and retrieval.
- Created `GitHubRepositorySpecification` for dynamic JPA queries.
- Developed `GitHubController` with search and retrieve endpoints.
- Implemented `GlobalExceptionHandler` for error handling.
- Updated `pom.xml` to Java 21 with necessary dependencies and OpenAPI annotations. 