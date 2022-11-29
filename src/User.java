import exceptions.LifetimeExpiredException;
import exceptions.NonceIsNotValidException;
import exceptions.TimestampAfterLifetimeException;
import exceptions.UserIdIsNotValidException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.util.Arrays;

public class User {
    private String username;
    private String userId;
    private String otherUserId;
    private String key;
    private byte[] nonce;
    private byte[] sessionKey;

    public User(String username, String userId, String otherUserId, String key) {
        this.username = username;
        this.userId = userId;
        this.otherUserId = otherUserId;
        this.key = key;
        this.nonce = new byte[16];
        this.nonce = Helper.generateNonce();
    }

    // Verifikacija na yA i yB kaj Alice
    public AliceToBobMessage verificationAlice(KDCtoAlice kdCtoAlice, KDCtoBob kdCtoBob) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        AES aesAlice = new AES();
        aesAlice.setKey(this.key);
        byte[] decryptedSessionKey = aesAlice.decrypt(kdCtoAlice.getCipheredSessionKey());
        byte[] decryptedNonce = aesAlice.decrypt(kdCtoAlice.getCipheredNonce());
        byte[] decryptedLifetime = aesAlice.decrypt(kdCtoAlice.getCipheredLifetime());
        byte[] decryptedIdBob = aesAlice.decrypt(kdCtoAlice.getCipheredIdBob());

        this.sessionKey = decryptedSessionKey;

        String lifetime = new String(decryptedLifetime);
        Time time = new Time(Long.parseLong(lifetime));
        Time currentTime = new Time(System.currentTimeMillis());

        if (!Arrays.equals(nonce, decryptedNonce)) {
            throw new NonceIsNotValidException();
        }
        String decryptedIdB = new String(decryptedIdBob);
        if (!otherUserId.equals(decryptedIdB)) {
            throw new UserIdIsNotValidException();
        }
        if (currentTime.after(time)) {
            throw new LifetimeExpiredException();
        }

        Time timestamp = Helper.generateTimestamp();

        AES aesSession = new AES();
        String SessionKey = new String(decryptedSessionKey);
        aesSession.setKey(SessionKey);

        byte[] cipheredIdA = aesSession.encrypt(userId.getBytes());
        byte[] cipheredTimestamp = aesSession.encrypt(String.valueOf(timestamp.getTime()).getBytes());

        AliceToBobMessage aliceToBobMessage = new AliceToBobMessage(cipheredIdA, cipheredTimestamp, kdCtoBob);
        return aliceToBobMessage;
    }

    //Verifikacija na yAB i yB kaj Bob
    public void verificationBob(AliceToBobMessage aliceToBobMessage) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        AES aesBob = new AES();
        aesBob.setKey(key);
        byte[] decryptedSessionKey = aesBob.decrypt(aliceToBobMessage.getKdCtoBob().getCipheredSessionKey());
        byte[] decryptedIdAlice = aesBob.decrypt(aliceToBobMessage.getKdCtoBob().getCipheredIdAlice());
        byte[] decryptedLifetime = aesBob.decrypt(aliceToBobMessage.getKdCtoBob().getCipheredLifetime());

        this.sessionKey = decryptedSessionKey;

        String lifetime = new String(decryptedLifetime);
        Time time = new Time(Long.valueOf(lifetime));

        AES aesSession = new AES();
        String sessionKey = new String(this.sessionKey);
        aesSession.setKey(sessionKey);

        byte[] decryptedIdA = aesSession.decrypt(aliceToBobMessage.getCipheredIdAlice());
        byte[] decryptedTimestamp = aesSession.decrypt(aliceToBobMessage.getCipheredTimestamp());

        if (!Arrays.equals(decryptedIdA, decryptedIdAlice)) {
            throw new UserIdIsNotValidException();
        }

        Time currentTime = new Time(System.currentTimeMillis());
        if (currentTime.after(time)) {
            throw new LifetimeExpiredException();
        }

        String timestampString = new String(decryptedTimestamp);
        Time timestamp = new Time(Long.valueOf(timestampString));
        if (timestamp.after(time)) {
            throw new TimestampAfterLifetimeException();
        }
    }

    //enripcija na porakata sto treba da se prati
    public byte[] encryptMessage(String message) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        AES aes = new AES();
        String key = new String(sessionKey);
        aes.setKey(key);
        System.out.println("    Originalna poraka za prakanje: " + message);
        byte[] encryptedMessage =  aes.encrypt(message.getBytes());
        System.out.println("    Enkriptirana poraka za prakanje: " + encryptedMessage);
        return encryptedMessage;
    }

    //dekripcija na primenata poraka
    public void decryptMessage(byte[] message) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        AES aes = new AES();
        String key = new String(sessionKey);
        aes.setKey(key);
        System.out.println("    Originalna primena poraka: " + message);
        byte[] decryptedMessage = aes.decrypt(message);
        String printMessage = new String(decryptedMessage);
        System.out.println("    Dekriptirana primena poraka: " + printMessage);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getNonce() {
        return nonce;
    }

    public void setNonce(byte[] nonce) {
        this.nonce = nonce;
    }

    public byte[] getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(byte[] sessionKey) {
        this.sessionKey = sessionKey;
    }
}

