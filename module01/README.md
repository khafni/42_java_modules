# Java Module 01 - Object-Oriented Programming and Collections

Welcome to Java Module 01! In this module, we'll delve into the world of object-oriented programming and collections in Java. You'll progressively build a money transfer application through a series of exercises. The primary focus is on modeling data, generating unique IDs, creating collections, and implementing the business logic.

## Table of Contents

1. [Exercise 00: Models](#exercise-00-models)
2. [Exercise 01: ID Generator](#exercise-01-id-generator)
3. [Exercise 02: List of Users](#exercise-02-list-of-users)
4. [Exercise 03: List of Transactions](#exercise-03-list-of-transactions)
5. [Exercise 04: Business Logic](#exercise-04-business-logic)
6. [Exercise 05: Menu](#exercise-05-menu)

## Exercise 00: Models

### Objective
In this exercise, we establish the foundation by creating basic domain models for User and Transaction classes.

### User Class
- Fields: Identifier, Name, Balance.
- Ensure each user has a unique ID.

### Transaction Class
- Fields: Identifier, Recipient (User type), Sender (User type), Transfer category (debits, credits), Transfer amount.

### Example
```java
User user = new User(1, "John", 1000);
Transaction transaction = new Transaction("1fc852e7-914f-4bfd-913d-0313aab1ed99", user1, user2, TransactionCategory.CREDIT, 150);
## Exercise 01: ID Generator

### Objective
To ensure user IDs are unique, create a UserIdsGenerator class.

- UserIdsGenerator keeps track of the last generated ID.
- User identifiers must be read-only and initialized when the object is created.
- The logic for initializing identifiers should be added to the User class constructor.

### Example
```java
int userId = UserIdsGenerator.getInstance().generateId();
```
## Exercise 01: ID Generator

### Objective

To ensure user IDs are unique, create a UserIdsGenerator class.

- UserIdsGenerator keeps track of the last generated ID.
- User identifiers must be read-only and initialized when the object is created.
- The logic for initializing identifiers should be added to the User class constructor.

### Example

javaCopy code

`int userId = UserIdsGenerator.getInstance().generateId();`
## Exercise 02: List of Users

### Objective
To manage users efficiently, implement a UsersList interface and UsersArrayList class.

### UsersList Interface
- Defines methods for adding and retrieving users by ID or index.

### UsersArrayList Class
- Implements UsersList using an array to store user data.
- Dynamically resizes its array when it becomes full.
- Handles UserNotFoundException when trying to retrieve a non-existent user.

### Example
```java
UsersList usersList = new UsersArrayList();
usersList.addUser(user1);
User retrievedUser = usersList.getUserById(1);
```

## Exercise 03: List of Transactions

### Objective
To manage transaction data, implement a TransactionsList interface and TransactionsLinkedList class.

### TransactionsList Interface
- Defines methods for adding transactions, removing transactions by ID, and transforming into an array.

### TransactionsLinkedList Class
- Implements TransactionsList using a linked list to store transactions.
- Handles TransactionNotFoundException when trying to remove a non-existent transaction.

### Example
```java
TransactionsList transactionsList = new TransactionsLinkedList();
transactionsList.addTransaction(transaction);
transactionsList.removeTransactionById("1fc852e7-914f-4bfd-913d-0313aab1ed99");
```

## Exercise 04: Business Logic

### Objective
Develop the TransactionsService class to implement the core business logic of the money transfer system.

- TransactionsService encapsulates user interactions and provides functionality for adding users, retrieving user balances, performing transfer transactions, retrieving transfers of a specific user, removing transactions by ID, and checking transfer validity.
- Handles IllegalTransactionException when attempting to transfer an amount exceeding the user's balance.

### Example
```java
TransactionsService transactionsService = new TransactionsService(usersList, transactionsList);
transactionsService.addUser("Mike", 100);
int balance = transactionsService.getUserBalance(2);
transactionsService.performTransfer(1, 2, 50);
```

## Exercise 05: Menu

### Objective
Create a console menu-driven application for the money transfer system.

- Menu functionality is implemented in the Menu class, which is linked to TransactionsService.
- Supports both production and dev modes.
- Displays a menu of options for adding users, viewing user balances, performing transfers, viewing transactions, removing transfers by ID, and checking transfer validity.

### Example
```shell
$> java Program --profile=dev
1. Add a user
2. View user balances
3. Perform a transfer
4. View all transactions for a specific user
5. DEV - remove a transfer by ID
6. DEV - check transfer validity
7. Finish execution
-> 1
Enter a user name and a balance
-> Jonh 777
User with id = 1 is added
```

These exercises provide a comprehensive introduction to object-oriented programming and collection handling in Java. By completing this module, you should have a solid foundation for building more complex Java applications.

**May the Force be with you!**
```

You can copy and paste this Markdown content into a README.md file for your Java Module 01 project. Feel free to further customize it to fit your needs.