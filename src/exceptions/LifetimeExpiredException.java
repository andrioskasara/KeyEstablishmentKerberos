package exceptions;

public class LifetimeExpiredException extends RuntimeException{
    public LifetimeExpiredException(){
        super("Lifetime has expired!");
    }
}
