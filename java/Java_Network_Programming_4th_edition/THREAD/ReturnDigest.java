import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/*
    지정된 파일의 다이제스트를 계산하는 Thread 서브클래스
 */
public class ReturnDigest extends Thread{
    private String filename;
    private byte[] digest;

    public ReturnDigest(String filename){
        this.filename = filename;
    }

    public void run(){
        try{
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while(din.read() != -1);
            din.close();
            digest = sha.digest();
        }catch(IOException ex){
            System.err.println(ex);
        }catch(NoSuchAlgorithmException ex){
            System.err.println(ex);
        }
    }

    public byte[] getDigest(){
        return digest;
    }
}
