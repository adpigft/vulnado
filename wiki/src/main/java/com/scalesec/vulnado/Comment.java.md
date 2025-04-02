# Documentation: `Comment.java`

## Overview
The `Comment` class is part of the `com.scalesec.vulnado` package and provides functionality for managing comments in a database. It includes methods for creating, fetching, and deleting comments, as well as persisting them to a PostgreSQL database. The class interacts with the database using JDBC.

---

## Class: `Comment`

### Attributes
The `Comment` class defines the following attributes:

| Attribute Name | Type       | Description                                      |
|----------------|------------|--------------------------------------------------|
| `id`           | `String`   | Unique identifier for the comment.              |
| `username`     | `String`   | Username of the user who created the comment.   |
| `body`         | `String`   | Content of the comment.                         |
| `created_on`   | `Timestamp`| Timestamp indicating when the comment was created. |

---

### Constructor
#### `Comment(String id, String username, String body, Timestamp created_on)`
Initializes a new `Comment` object with the provided attributes.

| Parameter      | Type       | Description                                      |
|----------------|------------|--------------------------------------------------|
| `id`           | `String`   | Unique identifier for the comment.              |
| `username`     | `String`   | Username of the user who created the comment.   |
| `body`         | `String`   | Content of the comment.                         |
| `created_on`   | `Timestamp`| Timestamp indicating when the comment was created. |

---

### Methods

#### `static Comment create(String username, String body)`
Creates a new comment and saves it to the database.

| Parameter      | Type       | Description                                      |
|----------------|------------|--------------------------------------------------|
| `username`     | `String`   | Username of the user who created the comment.   |
| `body`         | `String`   | Content of the comment.                         |

**Returns:**  
A `Comment` object if the creation and saving are successful. Throws exceptions if an error occurs.

**Exceptions:**  
- `BadRequest`: Thrown if the comment cannot be saved.
- `ServerError`: Thrown if a server-side error occurs.

---

#### `static List<Comment> fetch_all()`
Fetches all comments from the database.

**Returns:**  
A `List<Comment>` containing all comments retrieved from the database.

**Behavior:**  
- Executes a SQL query to fetch all rows from the `comments` table.
- Maps each row to a `Comment` object and adds it to the list.

---

#### `static Boolean delete(String id)`
Deletes a comment from the database based on its ID.

| Parameter      | Type       | Description                                      |
|----------------|------------|--------------------------------------------------|
| `id`           | `String`   | Unique identifier of the comment to be deleted. |

**Returns:**  
`true` if the deletion is successful, `false` otherwise.

**Behavior:**  
- Executes a SQL `DELETE` statement using a prepared statement.
- Returns `true` if one row is affected, indicating successful deletion.

---

#### `private Boolean commit() throws SQLException`
Commits the current `Comment` object to the database.

**Returns:**  
`true` if the insertion is successful, `false` otherwise.

**Behavior:**  
- Executes a SQL `INSERT` statement using a prepared statement.
- Maps the attributes of the `Comment` object to the corresponding database columns.

---

## Insights

### Security Concerns
1. **SQL Injection Risk**:  
   - The `fetch_all` method uses a raw SQL query (`stmt.executeQuery(query)`), which is vulnerable to SQL injection. Consider using prepared statements for all database interactions.

2. **Error Handling**:  
   - The `delete` method always returns `false` in the `finally` block, even if the deletion is successful. This can lead to incorrect behavior. Ensure proper handling of return values.

3. **Exception Management**:  
   - Exceptions are caught and printed (`e.printStackTrace()`), but not logged or rethrown in some cases. Use a logging framework for better error tracking.

---

### Database Dependency
The class relies on a `Postgres.connection()` method to establish a connection to the database. Ensure that the `Postgres` class is properly implemented and handles connection pooling to avoid resource leaks.

---

### UUID for Comment IDs
The `create` method generates a unique identifier for each comment using `UUID.randomUUID()`. This ensures that each comment has a globally unique ID, which is suitable for distributed systems.

---

### Timestamp Usage
The `created_on` attribute uses `Timestamp` to store the creation time of a comment. This is useful for tracking and sorting comments based on their creation time.

---

### Potential Improvements
1. **Validation**:  
   - Add input validation for `username` and `body` to prevent invalid or malicious data from being stored in the database.

2. **Pagination**:  
   - The `fetch_all` method retrieves all comments, which may not scale well for large datasets. Implement pagination to limit the number of comments fetched at once.

3. **Error Messages**:  
   - Provide more descriptive error messages in exceptions to help with debugging and user feedback.

---

## Metadata
| Key         | Value          |
|-------------|----------------|
| File Name   | `Comment.java` |
| Package     | `com.scalesec.vulnado` |
