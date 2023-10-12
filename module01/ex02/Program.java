
public class Program {
    public static void main(String[] args) {
        User user1 = new User("john", 300);
        // User user2 = new User(1, "bob", 300);
        // System.out.println(user1.toString());
        // System.out.println(user2.toString());
        UsersList ul = new UsersArrayList();
        // System.out.println(UserIdsGenerator.getInstance().generateId());
        ul.addUser(user1);
        try {
            ul.getUserById(1);
        } catch(UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}