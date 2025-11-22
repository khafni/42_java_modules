# Exercise 00: Registration - Socket Server & Client

This project implements a user registration system using socket communication between a client and server. The server uses Spring Framework with HikariCP connection pooling, JdbcTemplate for database operations, and BCrypt for secure password hashing.

## Architecture

### Server (`socket-server`)
```
socket-server/
└── src/main/java/fr/_42/sockets/
    ├── app/
    │   └── Main.java                    # Entry point
    ├── config/
    │   └── SocketsApplicationConfig.java # Spring configuration with beans
    ├── models/
    │   └── User.java                    # User entity model
    ├── repositories/
    │   ├── CrudRepository.java          # Generic CRUD interface
    │   ├── UsersRepository.java         # Users repository interface
    │   └── UsersRepositoryImpl.java     # Users repository implementation
    ├── services/
    │   ├── UsersService.java            # Users service interface
    │   └── UsersServiceImpl.java        # Users service implementation
    ├── server/
    │   └── Server.java                  # Socket server logic
    └── resources/
        ├── db.properties                # Database configuration
        └── schema.sql                   # Database schema

```

### Client (`socket-client`)
```
socket-client/
└── src/main/java/fr/_42/sockets/
    └── Client.java                      # Client application
```

## Prerequisites

1. **PostgreSQL** database installed and running
2. **Java 8** or higher
3. **Maven** for building the projects

## Setup

### 1. Database Setup

Create a PostgreSQL database:

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE chat_db;

# Exit psql
\q
```

### 2. Configure Database Connection

Edit `socket-server/src/main/resources/db.properties` if needed:

```properties
db.url=jdbc:postgresql://localhost:5432/chat_db
db.username=postgres
db.password=postgres
db.driver.class=org.postgresql.Driver
```

### 3. Build Projects

Build both server and client:

```bash
# Build server
cd socket-server
mvn clean package
cd ..

# Build client
cd socket-client
mvn clean package
cd ..
```

## Running the Application

### Start the Server

```bash
cd socket-server
java -jar target/socket-server-1.0-SNAPSHOT.jar --port=8081
```

The server will:
- Initialize the Spring application context
- Set up the database connection pool (HikariCP)
- Execute the schema.sql to create the users table
- Start listening on the specified port

### Start the Client

In a new terminal:

```bash
cd socket-client
java -jar target/socket-client-1.0-SNAPSHOT.jar --server-port=8081
```

## Usage Example

```
Hello from Server!
> signUp
Enter username:
> Marsel
Enter password:
> qwerty007
Successful!
```

After receiving the `Successful!` message, the connection will be closed automatically.

## Key Features

### Security
- **BCrypt Password Hashing**: Passwords are hashed using BCryptPasswordEncoder from Spring Security
- The hashed passwords are stored in the database, never plain text

### Spring Configuration
- **Component Scanning**: Automatic bean discovery for repositories, services, and components
- **DataSource Bean**: HikariCP connection pool configured as a Spring bean
- **JdbcTemplate Bean**: For database operations
- **PasswordEncoder Bean**: BCryptPasswordEncoder for password hashing
- **Database Initialization**: Automatic schema creation on startup

### Repository Pattern
- Generic `CrudRepository` interface for standard CRUD operations
- `UsersRepository` extends CrudRepository with user-specific methods
- Implementation uses JdbcTemplate for database access

### Service Layer
- `UsersService` handles business logic
- Checks for duplicate usernames
- Hashes passwords before storage
- Uses repository for data access

## Technical Details

### Dependencies (Server)
- Spring Framework (Context, JDBC)
- HikariCP for connection pooling
- PostgreSQL JDBC Driver
- Spring Security Crypto (BCrypt)
- SLF4J for logging

### Maven Plugins
- maven-compiler-plugin: Java compilation
- exec-maven-plugin: Run from command line
- maven-shade-plugin: Create executable JAR with dependencies

## Error Handling

The server handles various error cases:
- Duplicate username registration attempts
- Invalid database credentials
- Network connection issues
- Invalid command inputs

## Limitations

This implementation supports **only one concurrent client connection** as per the exercise requirements. Future exercises will extend this to support multiple concurrent users.

## Database Schema

```sql
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
```

## Notes

- The server runs indefinitely and accepts connections one at a time
- Each client connection is closed after the registration completes
- The database schema is automatically created on server startup
- Passwords are securely hashed using BCrypt before storage
