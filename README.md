# atipera-github-proxy

A proxy application that aggregates data from the GitHub API as a list of a user's non-fork repositories and their respective branches, created as part of a recruitment task for Atipera.

##  Technologies
* **Java 25**
* **Spring Boot 4.x** (Web, RestClient)
* **Gradle** (Kotlin DSL)
* **Wiremock Standalone 3.x** (for integration testing)

## How to run
The application does not require any additional configuration.

**1. Clone the repostiory**
```bash
git clone https://github.com/AdrianSzponarOnline/atipera-github-proxy.git
```
**2. Navigate to the project repository**
```bash
cd atipera-github-proxy
```
**3. Start the application**
```bash
./gradlew bootRun
```

## Example request

Once the application is running, you can test the endpoint using your terminal. The command below fetches the non-fork repositories and their branches for the GitHub user `octocat`:
```bash
curl -v http://localhost:8080/api/github/users/octocat/repos
```
*(Tip: You can replace `octocat` with any valid GitHub username, or test a non-existing user to see the custom 404 error response).*

## Testing
The project includes integration tests that do not make physical requests to the external GitHub API, thereby avoiding Rate Limit issues. All external API responses are fully mocked using an embedded Wiremock server.

**Run the test suite:**
```bash
./gradlew test
```