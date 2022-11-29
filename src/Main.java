import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        KDC kdc = new KDC("8e3MTnsHuQ","2hnsrYSSxl");
        String aliceID = UUID.randomUUID().toString();
        String bobID = UUID.randomUUID().toString();
        User Alice = new User("alice",aliceID,bobID,"8e3MTnsHuQ");
        User Bob = new User ("bob",bobID,aliceID,"2hnsrYSSxl");

        AnswerFromKDC answerFromKDC = kdc.request(Alice);

        AliceToBobMessage aliceToBobMessage = Alice.verificationAlice(answerFromKDC.getKdCtoAlice(), answerFromKDC.getKdCtoBob());
        Bob.verificationBob(aliceToBobMessage);

        System.out.println("\nTEST SCENARIO");
        System.out.println("\n ALICE PRAKJA PORAKA NA BOB");
        byte [] message = Alice.encryptMessage("Alice: Zdravo Bob!");
        System.out.println("\n BOB JA PRIMA PORAKATA");
        Bob.decryptMessage(message);

        System.out.println("\n BOB PRAKJA POVRATNA PORAKA NA ALICE");
        byte [] message2 = Bob.encryptMessage("Bob: Zdravo, kako si?");
        System.out.println("\n ALICE JA PRIMA PORAKATA");
        Alice.decryptMessage(message2);
    }
}