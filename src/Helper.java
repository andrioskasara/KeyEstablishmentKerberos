import java.security.SecureRandom;
import java.sql.Time;
import java.util.Random;

public class Helper {
    public static byte[] generateNonce() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] nonce = new byte[16];
        secureRandom.nextBytes(nonce);
        return nonce;
    }

    public static byte[] generateSessionKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] sessionKey = new byte[16];
        secureRandom.nextBytes(sessionKey);
        return sessionKey;
    }

    public static Time generateLifetime() {
        Time lifetime = new Time(System.currentTimeMillis() + (300 * 10 * 1000));
        return lifetime;
    }

    public static Time generateTimestamp() {
        Time timestamp = new Time(System.currentTimeMillis());
        return timestamp;
    }
}
