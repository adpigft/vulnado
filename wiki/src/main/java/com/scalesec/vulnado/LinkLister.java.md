# Documentation: `LinkLister.java`

## Overview
The `LinkLister` class provides functionality to extract hyperlinks from a given URL. It uses the `Jsoup` library for HTML parsing and includes methods to retrieve links with additional validation for private IP addresses.

---

## Class: `LinkLister`

### Purpose
The class is designed to fetch and process hyperlinks (`<a>` tags) from a webpage. It includes two methods:
1. `getLinks`: Retrieves all hyperlinks from a given URL.
2. `getLinksV2`: Adds validation to prevent fetching links from private IP addresses.

---

## Methods

### `getLinks(String url)`
#### Description
Fetches all hyperlinks (`<a>` tags) from the provided URL and returns them as a list of absolute URLs.

#### Parameters
| Name | Type   | Description                     |
|------|--------|---------------------------------|
| `url` | `String` | The URL of the webpage to parse. |

#### Returns
| Type          | Description                          |
|---------------|--------------------------------------|
| `List<String>` | A list of absolute URLs extracted from the webpage. |

#### Exceptions
| Type          | Description                          |
|---------------|--------------------------------------|
| `IOException` | Thrown if there is an issue connecting to the URL or parsing the webpage. |

#### Logic
1. Connects to the given URL using `Jsoup.connect(url).get()`.
2. Selects all `<a>` elements using `doc.select("a")`.
3. Extracts the absolute URL of each hyperlink using `link.absUrl("href")`.
4. Adds the URLs to a list and returns it.

---

### `getLinksV2(String url)`
#### Description
Fetches hyperlinks from the provided URL, but includes validation to block URLs pointing to private IP addresses.

#### Parameters
| Name | Type   | Description                     |
|------|--------|---------------------------------|
| `url` | `String` | The URL of the webpage to parse. |

#### Returns
| Type          | Description                          |
|---------------|--------------------------------------|
| `List<String>` | A list of absolute URLs extracted from the webpage. |

#### Exceptions
| Type          | Description                          |
|---------------|--------------------------------------|
| `BadRequest`  | Thrown if the URL points to a private IP address or if an error occurs during processing. |

#### Logic
1. Parses the URL using `new URL(url)` to extract the host.
2. Checks if the host starts with private IP address prefixes (`172.`, `192.168`, or `10.`).
   - If true, throws a `BadRequest` exception with the message "Use of Private IP".
3. If the host is valid, calls `getLinks(url)` to fetch the links.
4. Catches any exceptions and wraps them in a `BadRequest` exception.

---

## Insights

### Key Features
- **HTML Parsing**: Utilizes the `Jsoup` library to parse HTML and extract hyperlinks.
- **Private IP Validation**: Ensures that URLs pointing to private IP addresses are blocked for security purposes.
- **Error Handling**: Implements robust exception handling to manage invalid URLs or connection issues.

### Security Considerations
- **Private IP Blocking**: The `getLinksV2` method prevents access to private IP addresses, which is crucial for avoiding potential security risks such as SSRF (Server-Side Request Forgery).
- **Exception Wrapping**: Errors are encapsulated in a custom `BadRequest` exception, providing clear feedback to the caller.

### Dependencies
- **Jsoup Library**: Required for HTML parsing and element selection.
- **Java Networking**: Uses `java.net.URL` for URL validation and host extraction.

### Potential Enhancements
- **Customizable IP Validation**: Allow users to specify additional IP ranges or patterns to block.
- **Timeout Configuration**: Add support for configuring connection timeouts for `Jsoup.connect`.
- **Logging**: Implement detailed logging for debugging and monitoring purposes.

---

## Metadata
| Key         | Value              |
|-------------|--------------------|
| File Name   | `LinkLister.java`  |
| Package     | `com.scalesec.vulnado` |
