package exceptions;

public class UserIdIsNotValidException extends RuntimeException {
    public UserIdIsNotValidException() {
        super("User ID is not valid!");
    }
}
