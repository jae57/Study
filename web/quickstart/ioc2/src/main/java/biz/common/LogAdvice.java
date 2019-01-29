package biz.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class LogAdvice {

    @Before("PointcutCommon.allPointcut()")
    public void printLog(JoinPoint jp){
        System.out.println("[공통 로그] 비즈니스 로직 수행 전 동작");
    }
}
