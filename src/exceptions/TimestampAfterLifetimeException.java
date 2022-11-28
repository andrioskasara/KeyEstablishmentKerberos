package exceptions;

public class TimestampAfterLifetimeException extends RuntimeException{
    public TimestampAfterLifetimeException() {
        super("Timestamp is after the lifetime!");
    }
}
