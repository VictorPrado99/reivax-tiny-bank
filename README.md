# Reivax-Tiny-Bank

This repository showcases a REST API with a multi-layer architecture, including:

- Web Layer
- Service Layer
- Repository Layer

It also includes OpenAPI documentation, which can be accessed at `localhost:8080/swagger-ui`. This documentation is
sufficient for interacting with the API.

## How to Run

The project uses an in-memory database implemented with a `ConcurrentHashMap`, so there are no external dependencies or
Docker required.

To run the project:

1. Ensure that Gradle dependencies are installed. If you're using an IDE configured for Java or Kotlin, this should
   happen automatically.
2. If Gradle dependencies aren't installed automatically, you can manually run:

   ```bash
   ./gradlew build
