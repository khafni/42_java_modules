
public class Program {
    public static void main(String[] args) {
        User user1 = new User(0, "john", 300);
        User user2 = new User(1, "bob", 300);
        System.out.println(user1.toString());
        System.out.println(user2.toString());
        Transaction transaction1 = new Transaction(user1, user2, Transaction.TransferCategoryType.CREDIT, 50);
        System.out.println(transaction1);
        transaction1.makeTransaction();
        System.out.println(transaction1);

        Transaction transaction2 = new Transaction(user1, user2, Transaction.TransferCategoryType.DEBIT, -50);
        System.out.println(transaction2);
        transaction2.makeTransaction();
        System.out.println(user1.toString());
        System.out.println(user2.toString());
    }
}