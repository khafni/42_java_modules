package module01.ex03;

import java.util.UUID;

public interface TransactionsList {
    void add(Transaction transaction);
    void removeById(UUID id);
    Transaction[] toArray();
}
