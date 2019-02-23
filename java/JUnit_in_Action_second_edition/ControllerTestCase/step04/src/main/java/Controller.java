public interface Controller {
    /* 요청을 처리할 최상위 메서드.
    * 요청을 받으면, 컨트롤러는 이를 적절한 RequestHandler에게 전달한다.
    * processRequest는 아무런 예외도 선언하지 않았다.
    * 이 메서드는 제어 스택의 마지막에 해당하므로, 실행 과정 중 발생하는 모든 예외를 잡아 스스로 처리해야함.
    * 혹 제대로 대응하지 못하고 던져진 예외는 자바 가상 머신(JVM)이나 서블릿 컨테이너(servlet container)까지 도달할 수 있다.
    * 이렇게 되면 사용자는 자바 가상 머신이나 컨테이너가 뿌리는 딱딱하고 난해한 메시지와 마주하게 된다.
    * 직접 대응코드를 작성해 넣는 쪽이 백 번 낫다.*/
    Response processRequest(Request request);

    /* 아주 중요한 부분!!
    *  자바 소스를 수정하지 않고도 향후 손쉽게 기능을 확장할 수 있는 길을 터준다. */
    void addHandler(Request request, RequestHandler requestHandler);
}
