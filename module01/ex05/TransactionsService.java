import java.util.UUID;

public class TransactionsService {
    private UsersList usersList;
    private TransactionsList unpairedTrasanctions;
    
    public TransactionsService() {
        usersList = new UsersArrayList();
        unpairedTrasanctions = new TransactionsLinkedList();
    }

    public User addUser(String name, double balance) {
        User newUser = new User(name, balance);
        usersList.addUser(newUser);
        return newUser;
    }

    public User getUserById(int userId) {
        return usersList.getUserById(userId);
    }

    public String getUserNameById(int userId) {
        return usersList.getUserById(userId).getName();
    }

    public double getUserBalance(int userId) {
        User user = usersList.getUserById(userId);
        return user.getBalance();
    }

    public void performTransfer(int senderId, int recipientId, double transferAmount) {
        User sender = usersList.getUserById(senderId);
        User recipient = usersList.getUserById(recipientId);
        if (sender.getBalance() < transferAmount) {
            throw new IllegalTransactionException("insufficient Sender Balance > failed transfer");
        }
        Transaction transaction = new Transaction(recipient, sender, Transaction.TransferCategoryType.DEBIT, transferAmount);
        // Transaction transaction2 = new Transaction(recipient, sender, Transaction.TransferCategoryType.CREDIT, transferAmount);
        transaction.makeTransaction();
        sender.geTransactionsList().add(transaction);
        recipient.geTransactionsList().add(transaction);
    }

    public Transaction[] retrieveTransactionsByUser(User user) {
        return user.geTransactionsList().toArray();
    }

    public Transaction[] retrieveTransactionsByUserById(int id) {
        return usersList.getUserById(id).geTransactionsList().toArray();
    }

    public Transaction[] checkTransferValidity() {
        return unpairedTrasanctions.toArray();
    }

    public Transaction removeTransferById(int userId, String uuid) {
        User user = usersList.getUserById(userId);
        Transaction transaction = user.geTransactionsList().getTransactionById(UUID.fromString(uuid));
        unpairedTrasanctions.add(transaction);
        return transaction;
    }

}
