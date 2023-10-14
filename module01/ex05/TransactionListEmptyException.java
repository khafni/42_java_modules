public class TransactionListEmptyException extends RuntimeException{
    TransactionListEmptyException() {
        super("Transaction list is empty");
    }
    
}
