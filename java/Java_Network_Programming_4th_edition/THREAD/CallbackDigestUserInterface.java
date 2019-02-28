import javax.xml.bind.DatatypeConverter;

public class CallbackDigestUserInterface {
    // 이 메서드는 main() 메서드나 main()메서드의 실행 과정 중에 있는 어떠한 메서드에서도 호출되지 않으며,
    // 이미 독립적으로 실행 중인 각 스레드에 의해 호출된다.
    // 이 메서드는 main스레드가 아닌 다이제스트를 계산하는 스레드 안에서 실행된다.
    public static void receiveDigest(byte[] digest, String name){
        StringBuilder result = new StringBuilder(name);
        result.append(": ");
        result.append(DatatypeConverter.printHexBinary(digest));
        System.out.println(result);
    }

    public static void main(String[] args){
        for(String filename: args){
            //다이제스트 계산
            CallbackDigest cb = new CallbackDigest(filename);
            Thread t = new Thread(cb);
            t.start();
        }
    }
}
