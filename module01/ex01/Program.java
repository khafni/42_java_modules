
public class Program {
    public static void main(String[] args) {
        User user1 = new User( "john", 300);
        User user2 = new User("bob", 110);
        User user3 = new User("mike", 240);
        
        System.out.println(user1.toString());
        System.out.println(user2.toString());
        System.out.println(user3.toString());
    }
}