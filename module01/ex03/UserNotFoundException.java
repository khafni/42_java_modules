package module01.ex03;

public class UserNotFoundException extends RuntimeException  {
    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
