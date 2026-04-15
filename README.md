# AsteroidsApplication

This is an Asteroids game built with Java and JavaFX. Originally completed as part of the MOOC.fi Java programming course, the application has been adapted for the web using the JPRO framework and containerized for deployment.

### Technical Environment
* **Runtime:** Java 23
* **Frameworks:** JavaFX 21.0.2, JPRO 2025.3.3
* **Architecture:** Optimized for Apple Silicon (M4) and Linux-based containers

---
### Local Development (Desktop Mode)
To run the application as a native desktop window, you can use the terminal or your IDE.

**Using the Terminal:**
```bash
mvn clean compile javafx:run
```

**Using IntelliJ IDEA:**
1. Open the Maven tool window (`View > Tool Windows > Maven`).
2. Click the "Reload All Maven Projects" icon to ensure dependencies are synced.
3. Navigate to `Plugins > javafx > javafx:run` and double-click to execute.

   *Note: If IntelliJ does not automatically create a run configuration after the build, manually create a new Maven run configuration and set the command to `javafx:run`.*

---

### Web Server Mode (JPRO)
To run the application as a local web server without Docker, use the JPRO plugin:
```bash
mvn jpro:run
```
Once the server is running, the game is accessible in your browser at http://localhost:8080.

---

### Docker Deployment
The application is fully containerized. To run the web version via Docker, pull the image and map the container's port to your local machine:

```bash
docker run -p 8080:8080 shivvanir/asteroids-application
```
Once the container is active, the game is accessible at http://localhost:8080.

The official image can be found on [Docker Hub](https://hub.docker.com/r/shivvanir/asteroids-application).
