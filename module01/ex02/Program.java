
public class Program {
    public static void main(String[] args) {
        User user1 = new User("john", 300);
        User user2 = new User("bob", 300);
        // System.out.println(user1.toString());
        // System.out.println(user2.toString());
        UsersList ul = new UsersArrayList();
        // System.out.println(UserIdsGenerator.getInstance().generateId());
        ul.addUser(user1);
        ul.addUser(user2);
        System.out.println(ul.getUserById(0));
        System.out.println(ul.getUserById(1));
        // System.out.println(ul.getUserById(3));
        System.out.println(ul.getUsersNumbers());
        System.out.println(ul.getUserByIndex(0));


        // try {
        //     ul.getUserById(4);
        // } catch(UserNotFoundException e) {
        //     System.out.println(e.getMessage());
        // }
    }
}