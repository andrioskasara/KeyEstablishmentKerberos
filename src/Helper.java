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
        return new Time(System.currentTimeMillis() + 1800000);
    }

    public static Time generateTimestamp() {
        return new Time(System.currentTimeMillis());
    }
}
