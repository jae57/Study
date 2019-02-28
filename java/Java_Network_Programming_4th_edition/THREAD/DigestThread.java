import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.*; // DatatypeConverter 사용을 위한 패키지

/* Thread의 서브클래스를 만들었다면, run() 메서드를 오버라이드 하는 것 외에는 아무것도 하지 말아야 한다.
*  Thread클래스의 start(), interrupt(), join(), sleep()을 포함한 다른 메서드는 특별한 목적이나 가상 머신과 통신을 위해
* 사용된다. 따라서 run() 메서드를 오버라이드하고 필요한 추가적인 생성자와 메서드를 제공하는 것 외에는 Thread메서드의 어떤 메서드도
* 변경해서는 안 된다.
* */
public class DigestThread extends Thread {
    private String filename;

    public DigestThread(String filename){
        this.filename = filename;
    }

    // 스레드의 실제 작업은 run() 메서드 안에서 처리된다.
    // DigestInputStream 메서드를 사용하여 파일을 읽고 그 결과 다이제스트 값을 16진수로 인코딩하여 System.out을 통해 출력한다.
    // run() 메서드의 선언은 변경할 수 없기 때문에, run() 메서드로 인자를 전달하거나 반환받을 수 없다.
    // 결국 스레드로 정보를 전달하거나 반환받기 위한 다른 방법이 필요하다.
    // 정보를 전달하는 가장 쉬운 방법은 생성자의 인자로 전달하여 서브클래스의 인자를 설정하는 방법이다.
    // 스레드로부터 데이터를 반환받으려면 스레드의 비동기 특성 때문에 약간의 트릭이 필요하다.
    // 이 경우 호출한 스레드로 어떠한 정보도 반환하지 않고 단지 결과를 System.out에 출력하는 방법으로 이 문제를 피해갔다.
    public void run(){
        try{
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while (din.read() != -1);
            din.close();
            byte[] digest = sha.digest();

            // 출력할 모든 내용을 먼저 로컬 StringBuilder 변수인 result에 출력하여 전체 문자열을 만든 다음
            // 출력 메서드를 호출하여 한 번에 콘솔에 출력한다.
            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }catch(IOException ex){
            System.err.println(ex);
        }catch(NoSuchAlgorithmException ex){
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        for(String filename : args){
            Thread t = new DigestThread(filename);
            t.start();
        }
    }
    /* 일반적으로 스레드의 실행 결과를 프로그램의 어딘가로 전달할 필요가 있다.
        이때에는 계산한 결과를 필드에 저장하고 해당 필드의 값을 반환하는 메서드를 제공하는 방법을 사용할 수 있다.

        그러나, 언제 실행이 끝나서 필드 값이 설정되는지 알 수 있을까?
        만약 결과 값이 계산되기도 전에 누군가 메서드를 호출한다면 무엇을 반환해야 할까?
    * */
}


