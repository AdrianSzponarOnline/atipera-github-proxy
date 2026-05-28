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

## API Endpoints

| Method | Endpoint | Parameters | Description |
| :--- | :--- |:-----------| :--- |
| GET | `/api/github/users/{username}/repos` | `username` | Retrieves a list of non-fork repositories for the specified GitHub user |

## Example request

Once the application is running, you can test the endpoint using your terminal. The command below fetches the non-fork repositories and their branches for the GitHub user `adrianszponaronline`:
```bash
curl -v http://localhost:8080/api/github/users/adrianszponaronline/repos
```
*(Tip: You can replace `adrianszponaronline` with any valid GitHub username, or test a non-existing user to see the custom 404 error response).*

**Expected Response (200 OK):**
```json
[
  {
    "repositoryName": "hello-world",
    "ownerLogin": "adrianszponaronline",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "7fd1a60b01f91b314f59955a4e4d4e80d8edf11d"
      },
      {
        "name": "feature-branch",
        "lastCommitSha": "b5a415fb5c71120022416b2ef32230b050c54178"
      }
    ]
  }
]
```

**Expected Error Response (404 Not Found):**
If the provided GitHub username does not exist, the API will return error response in format:
```json
{
  "status": 404,
  "message": "Github user does not exist"
}
```
## Testing
The project includes integration tests that do not make physical requests to the external GitHub API, thereby avoiding Rate Limit issues. All external API responses are mocked using a Wiremock server.

**Run the test suite:**
```bash
./gradlew test
```