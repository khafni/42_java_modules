public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private int identifier;

    private UserIdsGenerator() {
        identifier = -1;
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
            return instance;
        }
        else
            return instance;
    }

    public int generateId() {
            identifier++;
        return identifier;
    }

}
