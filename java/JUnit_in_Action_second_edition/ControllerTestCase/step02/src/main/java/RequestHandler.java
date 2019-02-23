/* 제어 구조 역전 패턴
*
*  컨트롤러와 함께 핸들러를 등록하는 방식은 제어 구조 역전의 한 예임.
*  '전화하지마. 우리가 연락할께'라는 할리우드 원칙과 동일함.
*  ( 즉, 프레임워크가 내가 작성한 코드를 호출할 수 있게 하는 )
*
*  먼저 이벤트가 발생했을 때 처리할 핸들러 객체를 등록해두고, 그 이벤트가 발생하면
*  등록되어 있던 객체의 특정 메서드를 호출하는 방식. 제어 구조의 역전을 통해 개발자들은 프레임워크가
*  관리하는 이벤트 생명주기에 대해 신경쓸 필요 없이 원하는 이벤트 처리를 위한 자기만의 핸들러를
*  끼워넣을 수 있다.
* */

/* Request를 처리하여 Response를 반환한다. 도우미 컴포넌트. */
public interface RequestHandler {
    /* process 메서드에 넘겨질 클래스가 어떤 예외를 던질지는 알 수 없음. 따라서 모든 예외를
    * 포괄하는 Exception을 던진다. */
    Response process(Request request) throws Exception;
}