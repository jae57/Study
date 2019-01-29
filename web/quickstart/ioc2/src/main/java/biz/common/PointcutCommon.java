package biz.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutCommon {
    @Pointcut("execution(* biz..*Impl.*(..))")
    public void allPointcut(){}

    @Pointcut("execution(* biz..*Impl.get*(..))")
    public void getPointcut(){}
}
