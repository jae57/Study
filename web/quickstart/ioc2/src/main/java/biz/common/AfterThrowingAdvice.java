package biz.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class AfterThrowingAdvice {

    @AfterThrowing(pointcut="PointcutCommon.allPointcut()",throwing="exceptObj")
    public void exceptionLog(JoinPoint jp,Exception exceptObj){
        String method = jp.getSignature().getName();
        System.out.println("[예외 처리] "+method+"() 메서드 수행 중 발생된 에러 메시지 : "+exceptObj.getMessage());

        if(exceptObj instanceof IllegalArgumentException){
            System.out.println("부적합한 값이 입력되었습니다.");
        }
    }
}
