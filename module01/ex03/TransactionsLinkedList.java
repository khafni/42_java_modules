package module01.ex03;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    Transaction head;
    int size;


    public TransactionsLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void add(Transaction transaction) {
        if (head == null) {
            head = transaction;
        }
        Transaction tmp = head;
        while (tmp.getNext() != null) {
            tmp = tmp.getNext();
        }
        tmp.setNext(transaction);
        size++;
    }

    @Override
    public void removeById(UUID id){
        Transaction tmp = head;
        if (head != null && head.getIdentifier().equals(id))
            head = head.getNext();
        while (tmp != null && tmp.getNext() != null) {
            if (tmp.getNext().getIdentifier().equals(id)) {
                tmp.setNext(tmp.getNext().getNext());
            }
            tmp = tmp.getNext();
        }
    }

    @Override
    public Transaction[] toArray()
    {
        Transaction[] array = new Transaction[size];
        Transaction tmp = head;
        int i = 0;
        while (tmp != null) {
            array[i] = tmp;
            i++;
            tmp = tmp.getNext();
        }
        return array;
    }

}
