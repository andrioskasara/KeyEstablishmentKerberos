import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;

public class KDC {
    private final String keyAlice;
    private final String keyBob;

    public KDC(String keyAlice, String keyBob) {
        this.keyAlice = keyAlice;
        this.keyBob = keyBob;
    }

    public AnswerFromKDC request(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        byte[] randomSessionKey = Helper.generateSessionKey();
        Time lifetime = Helper.generateLifetime();

        AES aesAlice = new AES();
        aesAlice.setKey(this.keyAlice);
        byte[] cipheredSessionKeyAlice = aesAlice.encrypt(randomSessionKey);
        byte[] cipheredNonce = aesAlice.encrypt(user.getNonce());
        byte[] cipheredTimeAlice = aesAlice.encrypt(String.valueOf(lifetime.getTime()).getBytes());
        byte[] cipheredIdBob = aesAlice.encrypt(user.getOtherUserId().getBytes());
        KDCtoAlice kdCtoAlice = new KDCtoAlice(cipheredSessionKeyAlice, cipheredNonce, cipheredTimeAlice, cipheredIdBob);

        AES aesBob = new AES();
        aesBob.setKey(this.keyBob);
        byte[] cipheredSessionKeyBob = aesBob.encrypt(randomSessionKey);
        byte[] cipheredIdAlice = aesBob.encrypt(user.getUserId().getBytes());
        byte[] cipheredTimeBob = aesBob.encrypt(String.valueOf(lifetime.getTime()).getBytes());
        KDCtoBob kdCtoBob = new KDCtoBob(cipheredSessionKeyBob, cipheredIdAlice, cipheredTimeBob);

        return new AnswerFromKDC(kdCtoAlice, kdCtoBob);
    }
}
