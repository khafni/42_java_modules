import java.util.UUID;

class Transaction {
    private UUID identifier;
    private User recipient;
    private User sender;

    public enum TransferCategoryType {
        DEBIT,
        CREDIT
    };

    private TransferCategoryType type;
    private double transferAmount;
    private Transaction next;

    public Transaction(User recipient, User sender, TransferCategoryType type, double transferAmount) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.type = type;
        setTransferAmount(transferAmount);
        this.next = null;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipipient() {
        return this.recipient;
    }

    public User getSender() {
        return this.sender;
    }

    public TransferCategoryType getTransferCategory() {
        return type;
    }

    public double getTransferAmount() {
        return this.transferAmount;
    }

    public void setRecpipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setTransferCategory(TransferCategoryType type) {
        this.type = type;
    }

    public Transaction getNext() {
        return next;
    }

    public void setNext(Transaction next) {
        this.next = next;
    }

    public void setTransferAmount(double transferAmount) {
        if (type == TransferCategoryType.DEBIT && transferAmount > 0) { // money should be only debited from sender and
                                                                        // credited to recipient
            transferAmount *= -1;
        }
        if (type == TransferCategoryType.CREDIT && transferAmount < 0) { // money should be only credited to sender and
                                                                         // debited from recipient
            transferAmount *= -1;
        }
        this.transferAmount = transferAmount;
    }

    public void makeTransaction() {
        if (sender.getBalance() < transferAmount) {
            throw new IllegalTransactionException("insufficient Sender Balance > failed transfer");
        }
        sender.setBalance(sender.getBalance() + transferAmount);
        recipient.setBalance(recipient.getBalance() - transferAmount);
    }

    @Override
    public String toString() {
        return "{identifier " + identifier.toString()
                + ",\nsender: " + sender.toString() +
                ",\nrecipient: " + recipient.toString() +
                ", type: " + (type.equals(TransferCategoryType.DEBIT) ? "debits" : "credits")
                + ", transfer amount: " + transferAmount;
    }
}