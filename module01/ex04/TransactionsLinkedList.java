import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    Transaction head;
    Transaction tail;
    int size;


    public TransactionsLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public Transaction getHead() {
        return head;
    }
    
    public int getSize() {
        return size;
    }

    @Override
    public void add(Transaction transaction) {
        if (tail == null) {
            head = transaction;
            tail = transaction;
            size++;
            return ;
        }
        tail.setNext(transaction);
        tail = transaction;
        size++;
    }

    @Override
    public void removeById(UUID id){
        Transaction tmp = head;
        if (head != null && head.getIdentifier().equals(id)) {
            head = head.getNext();
            size--;
            return;
        }
        while (tmp != null && tmp.getNext() != null) {
            if (tmp.getNext().getIdentifier().equals(id)) {
                if (tail.equals(tmp.getNext()))
                    tail = tmp.getNext();
                tmp.setNext(tmp.getNext().getNext());
                size--;
                return ;
            }
            tmp = tmp.getNext();
        }
        throw new TransactionNotFoundException();
    }

    @Override
    public Transaction[] toArray()
    {
        if (size == 0)
            throw new TransactionListEmptyException();
        Transaction[] array = new Transaction[size];
        Transaction tmp = head;
        // System.out.println("size "+ size);
        int i = 0;
        while (tmp != null) {
            array[i] = tmp;
            i++;
            tmp = tmp.getNext();
        }
        return array;
    }

}
