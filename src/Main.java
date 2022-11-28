import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        KDCServer kdc = new KDCServer("8e3MTnsHuQ","2hnsrYSSxl");
        String aliceID = UUID.randomUUID().toString();
        String bobID = UUID.randomUUID().toString();
        User Alice = new User("alice",aliceID,bobID,"8e3MTnsHuQ");
        User Bob = new User ("bob",bobID,aliceID,"2hnsrYSSxl");
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