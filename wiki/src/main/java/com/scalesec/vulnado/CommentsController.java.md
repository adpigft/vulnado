# Documentation: `CommentsController.java`

## Overview

The `CommentsController` class is a RESTful controller implemented using the Spring Boot framework. It provides endpoints for managing comments, including fetching all comments, creating a new comment, and deleting an existing comment. The controller enforces authentication via a custom header (`x-auth-token`) and supports Cross-Origin Resource Sharing (CORS) for all origins.

## Features

- **Authentication**: Validates requests using a token-based authentication mechanism.
- **CORS Support**: Allows cross-origin requests from any origin.
- **Endpoints**:
  - Fetch all comments (`GET /comments`)
  - Create a new comment (`POST /comments`)
  - Delete a comment by ID (`DELETE /comments/{id}`)
- **Custom Exceptions**: Handles specific error scenarios with custom exceptions.

---

## Class Details

### 1. `CommentsController`

#### Annotations
- `@RestController`: Marks the class as a RESTful controller.
- `@EnableAutoConfiguration`: Enables Spring Boot's auto-configuration feature.
- `@CrossOrigin(origins = "*")`: Allows cross-origin requests from any origin.

#### Fields
- `@Value("${app.secret}") private String secret`: Injects the application secret from the configuration file for authentication purposes.

#### Methods

| HTTP Method | Endpoint            | Description                                                                 | Parameters                                                                                     | Return Type       |
|-------------|---------------------|-----------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|-------------------|
| `GET`       | `/comments`         | Fetches all comments.                                                      | `@RequestHeader("x-auth-token") String token`: Authentication token.                          | `List<Comment>`   |
| `POST`      | `/comments`         | Creates a new comment.                                                     | `@RequestHeader("x-auth-token") String token`: Authentication token.                          | `Comment`         |
|             |                     |                                                                             | `@RequestBody CommentRequest input`: Request body containing `username` and `body`.           |                   |
| `DELETE`    | `/comments/{id}`    | Deletes a comment by its ID.                                               | `@RequestHeader("x-auth-token") String token`: Authentication token.                          | `Boolean`         |
|             |                     |                                                                             | `@PathVariable("id") String id`: ID of the comment to delete.                                 |                   |

---

### 2. `CommentRequest`

A simple data structure used to represent the request body for creating a comment.

| Field Name | Type     | Description                     |
|------------|----------|---------------------------------|
| `username` | `String` | The username of the commenter. |
| `body`     | `String` | The content of the comment.    |

Implements `Serializable` for potential serialization needs.

---

### 3. `BadRequest`

A custom exception class that maps to the HTTP 400 (Bad Request) status code.

| Constructor Parameter | Type     | Description                     |
|-----------------------|----------|---------------------------------|
| `exception`           | `String` | The error message for the exception. |

---

### 4. `ServerError`

A custom exception class that maps to the HTTP 500 (Internal Server Error) status code.

| Constructor Parameter | Type     | Description                     |
|-----------------------|----------|---------------------------------|
| `exception`           | `String` | The error message for the exception. |

---

## Insights

1. **Authentication**:
   - The `User.assertAuth(secret, token)` method is used to validate the `x-auth-token` header. This ensures that only authenticated users can access the endpoints. However, the implementation of `User.assertAuth` is not provided in this file.

2. **CORS Configuration**:
   - The `@CrossOrigin(origins = "*")` annotation allows requests from any origin. While this is convenient for development, it may pose security risks in production environments. Consider restricting origins in production.

3. **Error Handling**:
   - Custom exceptions (`BadRequest` and `ServerError`) are defined to handle specific error scenarios. These exceptions are mapped to appropriate HTTP status codes (`400` and `500` respectively).

4. **Comment Management**:
   - The `Comment` class is assumed to handle the core logic for fetching, creating, and deleting comments. Its implementation is not included in this file.

5. **Scalability**:
   - The use of `@EnableAutoConfiguration` and Spring Boot's RESTful features makes the controller scalable and easy to integrate with other components.

6. **Security Considerations**:
   - The `secret` field is injected from the application's configuration. Ensure that this value is securely stored and not exposed in version control or logs.

7. **Serialization**:
   - The `CommentRequest` class implements `Serializable`, which may be useful for distributed systems or caching mechanisms.

---

## Potential Enhancements

- **Validation**: Add input validation for the `CommentRequest` fields to ensure data integrity.
- **Error Responses**: Provide detailed error responses for better debugging and user feedback.
- **Rate Limiting**: Implement rate limiting to prevent abuse of the endpoints.
- **Logging**: Add logging for better traceability and debugging.
