import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class InstanceCallbackDigest implements Runnable{
    private String filename;
    private InstanceCallbackDigestUserInterface callback;

    // 콜백을 호출하는 클래스(InstanceCallbackDigest)는 콜백될 클래스에 대한 레퍼런스를 알고 있어야 함.
    // 이 레퍼런스는 일반적으로 스레드의 생성자를 통해 전달된다.
    public InstanceCallbackDigest(String filename, InstanceCallbackDigestUserInterface callback){
        this.filename = filename;
        this.callback = callback;
    }

    public void run(){
        try{
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while(din.read() != -1);
            din.close();
            byte[] digest = sha.digest();
            // 결과를 전달하기 위해 콜백 객체의 인스턴스 메서드를 호출하고 있다.
            callback.receiveDigest(digest);
        }catch(IOException | NoSuchAlgorithmException ex){
            System.err.println(ex);
        }
    }
}
