# Documentation: `LoginController.java`

## Overview
The `LoginController` class is a REST API controller designed to handle user login requests. It validates user credentials and generates a token for successful authentication. The controller leverages Spring Boot annotations and integrates with a database for user authentication.

---

## File Metadata
- **File Name**: `LoginController.java`

---

## Components

### 1. **LoginController**
#### Description:
The main REST controller responsible for handling login requests.

#### Key Features:
- **Endpoint**: `/login`
  - **HTTP Method**: POST
  - **Consumes**: `application/json`
  - **Produces**: `application/json`
- **Cross-Origin Support**: Allows requests from any origin (`@CrossOrigin(origins = "*")`).
- **Authentication Logic**:
  - Fetches user details from the database using `User.fetch()`.
  - Compares the hashed password from the request with the stored hashed password.
  - Generates a token using the application's secret if authentication is successful.
  - Throws an `Unauthorized` exception if authentication fails.

#### Dependencies:
- **Spring Boot**:
  - `@RestController`
  - `@EnableAutoConfiguration`
  - `@RequestMapping`
  - `@CrossOrigin`
- **Spring Framework**:
  - `@Value` for injecting the application secret.
  - `@ResponseStatus` for defining HTTP status codes.

---

### 2. **LoginRequest**
#### Description:
A data structure representing the login request payload.

#### Fields:
| Field Name | Type     | Description                  |
|------------|----------|------------------------------|
| `username` | `String` | The username of the user.    |
| `password` | `String` | The password of the user.    |

#### Notes:
- Implements `Serializable` for potential serialization needs.

---

### 3. **LoginResponse**
#### Description:
A data structure representing the login response payload.

#### Fields:
| Field Name | Type     | Description                  |
|------------|----------|------------------------------|
| `token`    | `String` | The authentication token.    |

#### Constructor:
- Accepts a `String` message (token) and assigns it to the `token` field.

#### Notes:
- Implements `Serializable` for potential serialization needs.

---

### 4. **Unauthorized**
#### Description:
A custom exception class used to handle unauthorized access.

#### Features:
- Annotated with `@ResponseStatus(HttpStatus.UNAUTHORIZED)` to return a 401 HTTP status code.
- Extends `RuntimeException`.

#### Constructor:
- Accepts a `String` exception message and passes it to the superclass.

---

## Insights

### Security Considerations:
1. **Hardcoded Secret**:
   - The application secret is injected using `@Value("${app.secret}")`. Ensure that the secret is securely stored and not exposed in the codebase or environment variables.
   
2. **Password Hashing**:
   - The password is hashed using `Postgres.md5()`. Verify that the hashing algorithm is secure and up-to-date to prevent vulnerabilities.

3. **Cross-Origin Requests**:
   - The `@CrossOrigin(origins = "*")` annotation allows requests from any origin. This could expose the API to Cross-Origin Resource Sharing (CORS) attacks. Consider restricting origins to trusted domains.

4. **Exception Handling**:
   - The `Unauthorized` exception is used to handle failed authentication. Ensure that sensitive information is not exposed in the exception message.

### Scalability:
- The current implementation fetches user details and validates passwords synchronously. For high traffic scenarios, consider optimizing database queries and implementing caching mechanisms.

### Serialization:
- Both `LoginRequest` and `LoginResponse` implement `Serializable`. This is useful for distributed systems or scenarios requiring object serialization.

### Error Handling:
- The application throws a custom `Unauthorized` exception for failed authentication. Additional error handling mechanisms could be implemented for other potential issues (e.g., database connection errors).

---

## Endpoints Summary

| Endpoint   | HTTP Method | Request Type      | Response Type     | Description                     |
|------------|-------------|-------------------|-------------------|---------------------------------|
| `/login`   | POST        | `application/json` | `application/json` | Authenticates user credentials and returns a token. |

---

## Dependencies

### Frameworks and Libraries:
| Dependency                  | Purpose                                      |
|-----------------------------|----------------------------------------------|
| `Spring Boot`               | Provides REST API functionality and auto-configuration. |
| `Spring Framework`          | Enables dependency injection and HTTP status management. |
| `Postgres.md5()`            | Used for password hashing.                  |

### External Configuration:
- **Environment Variable**: `app.secret`  
  - Used to generate authentication tokens. Ensure secure storage and access.

---
