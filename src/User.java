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
        this.nonce = Helper.generateSessionKey();
    }

    // Verifikacija na yA i yB kaj Alice
    public AliceToBobMessage AliceVerification(KDCtoBob kdCtoBob, KDCtoAlice kdCtoAlice) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        AES aesAlice = new AES();
        aesAlice.setKey(this.key);
        byte [] decryptedSessionKey = aesAlice.decrypt(kdCtoAlice.getCipheredSessionKey());
        byte [] decryptedNonce = aesAlice.decrypt(kdCtoAlice.getCipheredNonce());
        byte [] decryptedLifetime = aesAlice.decrypt(kdCtoAlice.getCipheredLifetime());
        byte [] decryptedIdBob = aesAlice.decrypt(kdCtoAlice.getCipheredIdBob());

        this.sessionKey=decryptedSessionKey;

        String lifetime = new String(decryptedLifetime);
        Time time = new Time(Long.parseLong(lifetime));
        Time atThisMoment = new Time(System.currentTimeMillis());

        if(!Arrays.equals(nonce,decryptedNonce)){
            throw new NonceIsNotValidException();
        }
        String decryptedIdB = new String(decryptedIdBob);
        if(!otherUserId.equals(decryptedIdB)){
            throw new UserIdIsNotValidException();
        }
        if(atThisMoment.after(time)){
            throw new LifetimeExpiredException();
        }

        Time timestamp = Helper.generateTimestamp();

        AES aesSession = new AES();
        String SessionKey = new String(decryptedSessionKey);
        aesSession.setKey(SessionKey);

        byte[] cipheredIdA = aesSession.encrypt(userId.getBytes());
        byte[] cipheredTimestamp = aesSession.encrypt(String.valueOf(timestamp.getTime()).getBytes());

        AliceToBobMessage aliceToBobMessage = new AliceToBobMessage(cipheredIdA,cipheredTimestamp,kdCtoBob);
        return aliceToBobMessage;
    }

    //Verifikacija na yAB i yB kaj Bob
    public void BobVerification(AliceToBobMessage aliceToBobMessage) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        AES aesBob = new AES();
        aesBob.setKey(key);
        byte [] decryptedSessionKey = aesBob.decrypt(aliceToBobMessage.getKdCtoBob().getCipheredSessionKey());
        byte [] decryptedIdAlice = aesBob.decrypt(aliceToBobMessage.getKdCtoBob().getCipheredIdAlice());
        byte [] decryptedLifetime = aesBob.decrypt(aliceToBobMessage.getKdCtoBob().getCipheredLifetime());

        this.sessionKey = decryptedSessionKey;
        String lifetimeString = new String(decryptedLifetime);
        Time lifetime = new Time(Long.valueOf(lifetimeString));

        AES aesSession = new AES();
        String sessionKeyString = new String(sessionKey);
        aesSession.setKey(sessionKeyString);

        byte [] decryptedIDA = aesSession.decrypt(aliceToBobMessage.getCipheredIdAlice());
        byte [] decryptedTimestamp = aesSession.decrypt(aliceToBobMessage.getCipheredTimestamp());

        if(!Arrays.equals(decryptedIDA,decryptedIdAlice)){
            throw new UserIdIsNotValidException();
        }
        Time atThisMoment = new Time(System.currentTimeMillis());
        if(atThisMoment.after(lifetime)){
            throw new LifetimeExpiredException();
        }

        String timestampString = new String(decryptedTimestamp);
        Time timestamp = new Time(Long.valueOf(timestampString));
        if(timestamp.after(lifetime)){
            throw new TimestampAfterLifetimeException();
        }
    }

    //enripcija na porakata sto treba da se prati
    public byte[] getMessage(String message) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        AES aes = new AES();
        String keyString = new String(sessionKey);
        aes.setKey(keyString);
        return aes.encrypt(message.getBytes());
    }

    //dekripcija na primenata poraka
    public void readMessage(byte [] message) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        AES aes = new AES();
        String keyString = new String(sessionKey);
        aes.setKey(keyString);
        byte[] decryptedMessage = aes.decrypt(message);
        String printMessage = new String(decryptedMessage);
        System.out.println(printMessage);
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

