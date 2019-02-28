import javax.xml.bind.DatatypeConverter;

/*
    파일명을 전달받는 명령라인 사용자 인터페이스와 전달받은 파일에 대한 다이제스트를 계산하는 스레드를 생성하는 일을 한다.
 */
public class ReturnDigestUserInterface {
    public static void main(String[] args) {
        for(String filename : args){
            // 다이제스트 계산
            // 각각의 파일에 대해 새로운 ReturnDigest 스레드를 시작한다.
            ReturnDigest dr = new ReturnDigest(filename);
            dr.start();
            // 결과 출력
            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            byte[] digest = dr.getDigest(); // 계산된 다이제스트를 가져오려함.
            // 그러나 가져오기 전에 다이제스트가 계산이 끝났다고 장담할 수 없음.
            result.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(result);
        }
    }
}
