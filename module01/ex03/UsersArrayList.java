


public class UsersArrayList implements UsersList {
    User[] users;
    int numUsers;
    int capacity;

    public UsersArrayList() {
        users = new User[10];
        numUsers = 0;
        capacity = 10;
    }
    @Override
    public void addUser(User user) {
        if (numUsers == capacity)
        {
            capacity += capacity / 2;
            User[] usersTmp = new User[capacity];
            for (int i = 0; i < numUsers; i++) {
                usersTmp[i] = users[i];
            }
            users = usersTmp;
        }
        users[numUsers] = user;
        numUsers++;
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
            throw new UserNotFoundException("getUserByIndex: wrong index");
        return users[index];
    }

    @Override
    public int getUsersNumbers() {
        return numUsers;
    }

}
