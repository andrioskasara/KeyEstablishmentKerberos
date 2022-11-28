import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AES {

    private SecretKeySpec secretKey;
    private  byte[] key;

    public void setKey(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest messageDigest = null;
        this.key = key.getBytes("UTF-8");
        messageDigest = MessageDigest.getInstance("SHA-256");
        this.key = messageDigest.digest(this.key);
        this.key = Arrays.copyOf(this.key, 16);
        secretKey = new SecretKeySpec(this.key, "AES");
    }

    public byte[] decrypt(byte[] bytesToDecrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(bytesToDecrypt);
    }

    public byte[] encrypt(byte[] bytesToEncrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(bytesToEncrypt);
    }
}
