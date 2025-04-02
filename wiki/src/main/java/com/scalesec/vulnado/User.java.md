# Documentation: `User.java`

## Overview

The `User` class is a Java implementation that represents a user entity and provides functionality for user authentication, token generation, and database interaction. It is part of the `com.scalesec.vulnado` package and interacts with a PostgreSQL database to fetch user details. Additionally, it uses the `io.jsonwebtoken` library for JSON Web Token (JWT) operations.

---

## Class: `User`

### Fields

| Field Name       | Type     | Description                                      |
|------------------|----------|--------------------------------------------------|
| `id`             | `String` | Unique identifier for the user.                 |
| `username`       | `String` | Username of the user.                           |
| `hashedPassword` | `String` | Hashed password of the user.                    |

---

### Constructor

#### `User(String id, String username, String hashedPassword)`

Initializes a new `User` object with the provided `id`, `username`, and `hashedPassword`.

| Parameter         | Type     | Description                                      |
|-------------------|----------|--------------------------------------------------|
| `id`              | `String` | Unique identifier for the user.                 |
| `username`        | `String` | Username of the user.                           |
| `hashedPassword`  | `String` | Hashed password of the user.                    |

---

### Methods

#### `String token(String secret)`

Generates a JSON Web Token (JWT) for the user using the provided secret key.

| Parameter | Type     | Description                                      |
|-----------|----------|--------------------------------------------------|
| `secret`  | `String` | Secret key used to sign the JWT.                 |

**Returns:**  
A `String` representing the signed JWT.

---

#### `static void assertAuth(String secret, String token)`

Validates a given JWT token using the provided secret key. If the token is invalid, an `Unauthorized` exception is thrown.

| Parameter | Type     | Description                                      |
|-----------|----------|--------------------------------------------------|
| `secret`  | `String` | Secret key used to validate the JWT.             |
| `token`   | `String` | JWT token to be validated.                       |

**Throws:**  
- `Unauthorized` if the token is invalid.

---

#### `static User fetch(String un)`

Fetches a user from the database based on the provided username. The method queries the `users` table and returns a `User` object if a match is found.

| Parameter | Type     | Description                                      |
|-----------|----------|--------------------------------------------------|
| `un`      | `String` | Username to search for in the database.          |

**Returns:**  
A `User` object if the user is found, otherwise `null`.

**Database Query:**  
```sql
SELECT * FROM users WHERE username = '<username>' LIMIT 1;
```

---

## Insights

### Security Concerns

1. **SQL Injection Vulnerability**:  
   The `fetch` method constructs SQL queries using string concatenation, making it vulnerable to SQL injection attacks. This should be mitigated by using prepared statements or parameterized queries.

2. **Hardcoded Secret Key**:  
   The `token` and `assertAuth` methods rely on a secret key passed as a string. Ensure the secret key is securely managed and not hardcoded in the application.

3. **Exception Handling**:  
   The `assertAuth` method catches exceptions and rethrows them as `Unauthorized`. However, sensitive information from the exception stack trace may be exposed. Avoid printing stack traces in production environments.

4. **Password Storage**:  
   The `hashedPassword` field suggests that passwords are hashed. Ensure a strong hashing algorithm (e.g., bcrypt, Argon2) is used for secure password storage.

---

### Dependencies

- **`io.jsonwebtoken` Library**:  
  Used for JWT creation and validation. Ensure the library version is up-to-date to avoid vulnerabilities.

- **`javax.crypto.SecretKey`**:  
  Used for cryptographic operations related to JWT signing.

- **Database Connection**:  
  The `fetch` method relies on a `Postgres.connection()` method, which is assumed to provide a valid database connection. Ensure proper connection pooling and resource management.

---

### Potential Enhancements

1. **Use Prepared Statements**:  
   Replace the raw SQL query in the `fetch` method with a prepared statement to prevent SQL injection.

2. **Token Expiry**:  
   Add an expiration claim (`exp`) to the JWT in the `token` method for enhanced security.

3. **Error Logging**:  
   Replace `System.out.println` and `e.printStackTrace()` with a proper logging framework (e.g., SLF4J, Logback).

4. **Input Validation**:  
   Validate the `username` input in the `fetch` method to ensure it meets expected criteria.

5. **Resource Management**:  
   Ensure the `Statement` and `Connection` objects are properly closed in a `finally` block or use a try-with-resources statement.

---

### Risks

- **SQL Injection**: The current implementation is highly susceptible to SQL injection attacks.
- **Token Mismanagement**: Improper handling of JWT tokens can lead to security vulnerabilities.
- **Database Connection Leaks**: Failure to close the database connection properly may result in resource exhaustion.
