import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        KDCServer kdc = new KDCServer("informationSecurity42424324","laboratory1232442332");
        User Alice = new User("alice","IDalice","IDbob","informationSecurity42424324");
        User Bob = new User ("bob","IDbob","IDalice","laboratory1232442332");
        AnswerFromKDC answerFromKDC = kdc.request(Alice);
        AliceToBobMessage aliceToBobMessage = Alice.AliceVerification(answerFromKDC.getKdCtoBob(),
                answerFromKDC.getKdCtoAlice());
        Bob.BobVerification(aliceToBobMessage);
        System.out.println("===TEST===");
        byte [] message = Alice.getMessage("    Alice: Hey Bob!");
        Bob.readMessage(message);
        System.out.println("===RETURN MESSAGE===");
        byte [] message2 = Bob.getMessage("    Bob: Hey, what's up?");
        Alice.readMessage(message2);
        System.out.println("===END OF THE CHAT===");
    }
}