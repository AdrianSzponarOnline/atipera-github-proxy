package com.atipera.githubproxy;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "github.api.url=http://localhost:8089"
)
@WireMockTest(httpPort = 8089)
class GithubProxyApplicationTests {
    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void setup() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }
    @Test
    void shouldReturn404WhenUserDoesNotExist() {
        String username = "nonExistingUser";
        stubFor(get(urlEqualTo("/users/" + username + "/repos"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Not Found\"}")));


        HttpClientErrorException.NotFound exception = assertThrows(
                HttpClientErrorException.NotFound.class,
                () -> restClient.get()
                        .uri("/api/github/users/{username}/repos", username)
                        .retrieve()
                        .toBodilessEntity()
        );


        ErrorResponse errorResponse = exception.getResponseBodyAs(ErrorResponse.class);

        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.status()).isEqualTo(404);
        assertThat(errorResponse.message()).isEqualTo("Github user does not exist");
    }

    @Test
    void shouldReturnOnlyNonForkRepositoriesWithBranches() {

        String username = "testUser";

        stubFor(get(urlEqualTo("/users/" + username + "/repos"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [
                                  {
                                    "name": "repo-valid",
                                    "owner": { "login": "testUser" },
                                    "fork": false
                                  },
                                  {
                                    "name": "repo-fork",
                                    "owner": { "login": "testUser" },
                                    "fork": true
                                  }
                                ]
                                """)));

        stubFor(get(urlEqualTo("/repos/" + username + "/repo-valid/branches"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [
                                  {
                                    "name": "main",
                                    "commit": { "sha": "12345abcde" }
                                  }
                                ]
                                """)));


        RepoResponse[] response = restClient.get()
                .uri("/api/github/users/{username}/repos", username)
                .retrieve()
                .body(RepoResponse[].class);


        assertThat(response).isNotNull();
        assertThat(response).hasSize(1);

        RepoResponse repo = response[0];
        assertThat(repo.repositoryName()).isEqualTo("repo-valid");
        assertThat(repo.ownerLogin()).isEqualTo("testUser");
        assertThat(repo.branches()).hasSize(1);
        assertThat(repo.branches().getFirst().branchName()).isEqualTo("main");
        assertThat(repo.branches().getFirst().lastCommitSha()).isEqualTo("12345abcde");
    }
}
