import java.util.UUID;

public interface TransactionsList {
    void add(Transaction transaction);
    void removeById(UUID id);
    Transaction getTransactionById(UUID id);
    Transaction[] toArray();
}
