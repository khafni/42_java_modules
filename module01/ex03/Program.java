package module01.ex03;

public class Program {
    //5 users
    User user1 = new User("bob", 20);
    User user2 = new User("alice", 10);
    User user3 = new User("john", 30);
    User user4 = new User("jane", 40);
    User user5 = new User("joe", 50);

    //3 of transactions
    Transaction transaction1 = new Transaction(user1, user2, Transaction.TransferCategoryType.DEBIT, 10);
    Transaction transaction2 = new Transaction(user2, user3, Transaction.TransferCategoryType.DEBIT, 4);
    Transaction transaction3 = new Transaction(user3, user4, Transaction.TransferCategoryType.DEBIT, 4);
    
    // TransactionsList transactionsList = new TransactionsLinkedList();
    // transactionsList.add(transaction1);

}
