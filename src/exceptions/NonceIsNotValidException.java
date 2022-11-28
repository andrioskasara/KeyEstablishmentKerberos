package exceptions;

public class NonceIsNotValidException extends RuntimeException {
    public NonceIsNotValidException() {
        super("Nonce value is different!");
    }
}
