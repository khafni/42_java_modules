package module01.ex03;


public class UsersArrayList implements UsersList {
    User[] users;
    int numUsers;

    public UsersArrayList() {
        users = new User[10];
        numUsers = 0;
    }
    @Override
    public void addUser(User user) {
        if (numUsers == 10)
        {
            User[] usersTmp = new User[numUsers + (numUsers / 2)];
            for (int i = 0; i < numUsers; i++) {
                usersTmp[i] = users[i];
            }
            users = usersTmp;
        }
        users[numUsers] = user;

    }
    @Override
    public  User getUserById(int id) throws UserNotFoundException{
        for (int i = 0; i < numUsers; i++) {
            if (users[i].getIdentifier() == id)
                return users[i];
        }
        
        throw new UserNotFoundException("getUserByID: id not found");
    }

    @Override
    public User getUserByIndex(int index) throws UserNotFoundException
    {
        if (index < 0 || index >= numUsers)
            return users[index];
        throw new UserNotFoundException("getUserByIndex: wrong index");
    }

    @Override
    public int getUsersNumbers() {
        return numUsers;
    }

}
