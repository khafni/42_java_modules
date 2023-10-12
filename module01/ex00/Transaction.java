import java.util.UUID;


class Transaction {
    private UUID identifier;
    private User recipient;
    private User sender;
    public enum TransferCategoryType  {
        DEBIT,
        CREDIT
    };
    private TransferCategoryType type;
    private double transferAmount;

    public Transaction (User recipient, User sender, TransferCategoryType  type, double transferAmount) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender; 
        this.type = type;
        setTransferAmount(transferAmount);
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipipient () {
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

    public void setTransferAmount(double transferAmount) {
        if (type == TransferCategoryType.DEBIT && transferAmount > 0) { // money should be only debited from sender and credited to recipient
            System.err.println("Error: a debit transfer can't be postive");
            System.exit(1);
        }
        if (type == TransferCategoryType.CREDIT && transferAmount < 0) { // money should be only credited to sender and debited from recipient
            System.err.println("Error: a credit transfer can't be negative");
            System.exit(1);
        }
        this.transferAmount = transferAmount;
    }

    public void makeTransaction() {
            sender.setBalance(sender.getBalance() + transferAmount);
            recipient.setBalance(recipient.getBalance() - transferAmount);
    }

    @Override
    public String toString() {
        return "{identifier " + identifier.toString()
        + ",\nsender: " + sender.toString() +
        ",\nrecipient: "+ recipient.toString() +
        ", type: " + (type.equals(TransferCategoryType.DEBIT)? "debits": "credits")
        + ", transfer amount: " + transferAmount;
    }
}