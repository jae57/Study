import java.util.HashMap;
import java.util.Map;

public class DefaultController implements Controller {

    // 요청 핸들러들을 등록할 HashMap 선언
    private Map requestHandlers = new HashMap();

    // 주어진 요청에 적합한 RequestHandler를 반환하는 getHandler
    protected RequestHandler getHandler(Request request){

        // RequestHandler를 등록하지 않고, 호출할 경우
        if(!this.requestHandlers.containsKey(request.getName())){
            // RuntimeException을 던진다. 사용자나 외부시스템보다는 프로그래밍 실수에 의해 발생한 상황이라는 의미다.
            // RuntimeException은 메서드 시그니처에 명시되지 않더라도 던지고 받아 처리할 수 있는 예외임.
            // 물론 명시적인 예외를 정의해 컨트롤러 프레임워크에 추가하는 것이 더 나은 설계라 볼 수 있다.
            String message = "Cannot find handler for request name ["+request.getName()+"]";
            throw new RuntimeException(message);
        }

        // 호출자에게 적절한 핸들러를 반환
        return (RequestHandler) this.requestHandlers.get(request.getName());
    }

    /* 컨트롤러 클래스의 핵심!!
    * 요청을 적절한 핸들러에 전달하고, 그 핸들러의 응답을 반환한다.
    * */
    public Response processRequest(Request request) {
        Response response;
        try{
            response=getHandler(request).process(request);
        }catch(Exception exception){
            // 예외가 발생한다면 ErrorResponse 클래스로 한번 감싸서 반환한다.
            response=new ErrorResponse(request,exception);
        }
        return response;
    }

    /* 요청객체가 전달은 되는데, 정작 사용되는 것은 이름뿐임.
        ==> 오버디자인 ( 코드 작성보다 인터페이스를 먼저 정의할 때 종종 발생함.
        이를 피하는 효과적인 방법 중 하나가 테스트 주도 개발 )*/
    public void addHandler(Request request, RequestHandler requestHandler) {

        // 핸들러 이름의 등록 여부를 확인
        if(this.requestHandlers.containsKey(request.getName())){
            // 이미 등록된 이름이면 예외를 던짐.
            throw new RuntimeException("A request handler has already been registered for request name ["+request.getName()+"]");
        }else{
            this.requestHandlers.put(request.getName(),requestHandler);
        }
    }
}
