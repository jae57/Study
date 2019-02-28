import javax.xml.bind.DatatypeConverter;

/*
    파일명을 전달받는 명령라인 사용자 인터페이스와 전달받은 파일에 대한 다이제스트를 계산하는 스레드를 생성하는 일을 한다.
 */
public class ReturnDigestUserInterface2 {
    public static void main(String[] args) {
        ReturnDigest[] digests = new ReturnDigest[args.length];

        for(int i=0;i<args.length;i++){
            //다이제스트 계산
            digests[i] = new ReturnDigest(args[i]);
            digests[i].start();
        }

        for(int i=0; i< args.length; i++){
            //결과 출력
            StringBuffer result = new StringBuffer(args[i]);
            result.append(": ");
            byte[] digest = digests[i].getDigest();
            result.append(DatatypeConverter.printHexBinary(digest));

            System.out.println(result);
        }
    }
}
