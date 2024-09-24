# TaskManagerApp

## Prerequisites

- Java 17 or above
- IntelliJ IDEA
- Docker (optional, for running PostgreSQL in a container)

## 1. Installing Java and IntelliJ IDEA

### Java

1. Download and install Java 17 or above from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use a package manager like SDKMAN:
    ```sh
    sdk install java [version]
    ```

### IntelliJ IDEA

1. Download and install IntelliJ IDEA from the [official JetBrains website](https://www.jetbrains.com/idea/download/).

## 2. Cloning the Repository

1. Clone the repository using Git:
    ```sh
    git clone https://github.com/CYIMANA-Faisal/task-manager-api.git
    cd task-manager-api
    ```

## 3. Adding .env File

1. Copy the `.env-sample` file to `.env`:
    ```sh
    cp .env-sample .env
    ```

2. Update the `.env` file with your configuration details if necessary.

## 4. Running a Docker PostgreSQL Container

1. Run the following command to start a PostgreSQL container:
    ```sh
    docker run --name taskmanager-postgres -e POSTGRES_USER=youruser -e POSTGRES_PASSWORD=your-password -e POSTGRES_DB=taskmanager_db -d -p 5432:5432 postgres
    ```

## 5. Running the Application

1. Open the project in IntelliJ IDEA.

2. Navigate to `src/main/resources/application.properties` and ensure the database configuration matches your `.env` file.

3. Run the application using the following Maven command:
    ```sh
    ./mvnw spring-boot:run
    ```

4. Alternatively, you can run the `TaskManagerApp` class directly from IntelliJ IDEA.

## Additional Information

- The application will be accessible at `http://localhost:8080`.
- Ensure that the PostgreSQL database is running and accessible before starting the application.