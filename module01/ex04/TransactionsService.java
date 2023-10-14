public class TransactionsService {
    private UsersList usersList;
    private TransactionsList unpairedTrasanctions;
    
    public TransactionsService() {
        usersList = new UsersArrayList();
        unpairedTrasanctions = new TransactionsLinkedList();
    }

    public void addUser(String name, int balance) {
        User newUser = new User(name, balance);
        usersList.addUser(newUser);
    }

    public double getUserBalance(int userId) {
        User user = usersList.getUserById(userId);
        return user.getBalance();
    }

    public void performTransfer(int senderId, int recipientId, double transferAmount) {
        User sender = usersList.getUserById(senderId);
        User recipient = usersList.getUserById(recipientId);
        if (sender.getBalance() < transferAmount) {
            throw new IllegalTransactionException();
        }
        Transaction transaction = new Transaction(recipient, sender, Transaction.TransferCategoryType.DEBIT, transferAmount);
        transaction.makeTransaction();
        sender.geTransactionsList().add(transaction);
        recipient.geTransactionsList().add(transaction);
    }

    public Transaction[] retrieveTransactionsByUser(User user) {
        return user.geTransactionsList().toArray();
    }

    public Transaction[] checkTransferValidity() {
        return unpairedTrasanctions.toArray();
    }
}
