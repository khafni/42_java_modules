public class Program {
    public static void main(String[] args) {
        TransactionsService transactionsService = new TransactionsService();
        // 5 users
        // User user1 = new User("bob", 20);
        // User user2 = new User("alice", 10);
        // User user3 = new User("john", 30);
        // User user4 = new User("jane", 40);
        transactionsService.addUser("bob", 500);
        transactionsService.addUser("alice", 10);
        transactionsService.addUser("john", 30);
        transactionsService.addUser("jane", 40);

        transactionsService.performTransfer(1, 2, 30);

        System.out.println(transactionsService.getUserBalance(1));

        // // // 3 of transactions
        // Transaction transaction1 = new Transaction(user1, user2, Transaction.TransferCategoryType.CREDIT, 10);
        // Transaction transaction2 = new Transaction(user2, user3, Transaction.TransferCategoryType.CREDIT, 4);
        // Transaction transaction3 = new Transaction(user3, user4, Transaction.TransferCategoryType.DEBIT, -4);

        // TransactionsList transactionsList = new TransactionsLinkedList();
        // transactionsList.add(transaction1);
        // transactionsList.add(transaction2);
        // transactionsList.add(transaction3);
        
        // transactionsList.removeById(transaction3.getIdentifier());
        // Transaction[] transactions = transactionsList.toArray();
        // for (Transaction transaction : transactions) {
        //     System.out.println(transaction.toString() + "\n");
        // }
    }

}
