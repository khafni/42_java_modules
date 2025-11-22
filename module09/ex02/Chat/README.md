# Exercise 01: Messaging - Multi-User Chat Application

This project implements a multi-user chat system using socket communication between clients and a server. The server supports user registration, authentication, real-time messaging between multiple connected users, and message persistence to a database. The implementation uses Spring Framework with HikariCP connection pooling, JdbcTemplate for database operations, and BCrypt for secure password hashing.

## Architecture

### Server (`socket-server`)
```
socket-server/
└── src/main/java/fr/_42/sockets/
    ├── app/
    │   └── Main.java                       # Entry point
    ├── config/
    │   └── SocketsApplicationConfig.java   # Spring configuration with beans
    ├── models/
    │   ├── User.java                       # User entity model
    │   └── Message.java                    # Message entity model
    ├── repositories/
    │   ├── CrudRepository.java             # Generic CRUD interface
    │   ├── UsersRepository.java            # Users repository interface
    │   ├── UsersRepositoryImpl.java        # Users repository implementation
    │   ├── MessagesRepository.java         # Messages repository interface
    │   └── MessagesRepositoryImpl.java     # Messages repository implementation
    ├── services/
    │   ├── UsersService.java               # Users service interface
    │   ├── UsersServiceImpl.java           # Users service implementation
    │   ├── MessagesService.java            # Messages service interface
    │   └── MessagesServiceImpl.java        # Messages service implementation
    ├── server/
    │   ├── Server.java                     # Multi-threaded socket server
    │   └── ClientHandler.java              # Handles individual client connections
    └── resources/
        ├── db.properties                   # Database configuration
        └── schema.sql                      # Database schema

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

## Usage Examples

### User Registration

```
Hello from Server!
> signUp
Enter username:
> Marsel
Enter password:
> qwerty007
Successful!
```

After registration, the connection will be closed automatically.

### User Sign In and Messaging

```
Hello from Server!
> signIn
Enter username:
> Marsel
Enter password:
> qwerty007
Start messaging
> Hello!
Marsel: Hello!
NotMarsel: Bye!
> Exit
You have left the chat.
```

**Note:** To test multi-user messaging, you need to run multiple client instances simultaneously. Each client can sign in with different credentials and all messages will be broadcast to all connected users.

## Key Features

### Multi-User Chat Functionality
- **User Registration**: New users can create accounts with unique usernames
- **User Authentication**: Sign in with username and password verification
- **Real-Time Messaging**: Messages are broadcast to all connected clients simultaneously
- **Message Persistence**: All messages are saved to the database with sender info and timestamp
- **Graceful Logout**: Users can exit the chat cleanly with the "Exit" command

### Multi-Threading
- **Concurrent Client Connections**: Server supports multiple simultaneous client connections
- **Thread-per-Client Model**: Each client connection runs in its own thread
- **Thread-Safe Operations**: Synchronized collections for managing connected clients

### Security
- **BCrypt Password Hashing**: Passwords are hashed using BCryptPasswordEncoder from Spring Security
- **Password Verification**: Sign-in uses BCrypt matching to verify passwords
- **Hashed Storage**: Passwords are never stored in plain text

### Spring Configuration
- **Component Scanning**: Automatic bean discovery for repositories, services, and components
- **DataSource Bean**: HikariCP connection pool configured as a Spring bean
- **JdbcTemplate Bean**: For database operations
- **PasswordEncoder Bean**: BCryptPasswordEncoder for password hashing
- **Database Initialization**: Automatic schema creation on startup

### Repository Pattern
- Generic `CrudRepository` interface for standard CRUD operations
- `UsersRepository` extends CrudRepository with user-specific methods
- `MessagesRepository` for message storage and retrieval
- Implementation uses JdbcTemplate for database access

### Service Layer
- `UsersService` handles user business logic:
  - User registration with duplicate checking
  - User authentication with password verification
- `MessagesService` handles message operations:
  - Saving messages with timestamp
  - Retrieving message history

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

## User Lifecycle

Exercise 01 implements the complete chat user lifecycle:

1. **Registration** (`signUp` command)
   - Enter username and password
   - Password is hashed with BCrypt
   - User is saved to database

2. **Sign In** (`signIn` command)
   - Enter username and password
   - Server verifies credentials
   - If successful, user enters messaging mode

3. **Sending Messages**
   - User types messages after signing in
   - Each message is saved to database with sender and timestamp
   - Message is broadcast to all connected clients
   - Format: `Username: message text`

4. **Logout** (`Exit` command)
   - User types "Exit" to leave the chat
   - Connection is closed gracefully
   - User is removed from connected clients list

## Database Schema

```sql
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    sender_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    text TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## Testing Multi-User Chat

To test the multi-user functionality, you need to run multiple client instances:

1. **Terminal 1** - Start the server:
   ```bash
   cd socket-server
   java -jar target/socket-server-1.0-SNAPSHOT.jar --port=8081
   ```

2. **Terminal 2** - First client (User "Marsel"):
   ```bash
   cd socket-client
   java -jar target/socket-client-1.0-SNAPSHOT.jar --server-port=8081
   # Choose signIn, enter credentials, start messaging
   ```

3. **Terminal 3** - Second client (User "NotMarsel"):
   ```bash
   cd socket-client
   java -jar target/socket-client-1.0-SNAPSHOT.jar --server-port=8081
   # Choose signIn, enter different credentials, start messaging
   ```

4. **Test Message Broadcasting**:
   - Type a message in Terminal 2
   - Observe the message appears in both Terminal 2 and Terminal 3
   - Type a message in Terminal 3
   - Observe the message appears in both terminals

## Notes

- The server runs indefinitely and accepts multiple concurrent connections
- Each client connection runs in its own thread
- Messages are broadcast to all connected clients in real-time
- The database schema is automatically created on server startup
- Passwords are securely hashed using BCrypt before storage
- All messages are persisted to the database with sender and timestamp information
