package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // @Before
            log.info("[트랜잭션 시작] joinPoint.getSignature()={}", joinPoint.getSignature());

            Object result = joinPoint.proceed(); // Around에서 proceed 안 할 경우 체인 끊겨서 target 코드 실행 안 됨..

            // @AfterReturning
            log.info("[트랜잭션 커밋] joinPoint.getSignature()={}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] joinPoint.getSignature()={}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] joinPoint.getSignature()={}", joinPoint.getSignature());
        }
    }

    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        // 간단하게 사용하는 Advice. proceed 필요 없음.
        log.info("[Before] joinPoint.getSignature()={}", joinPoint.getSignature());
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        // result 값 변경할수 없음, return하지 않음
        log.info("[AfterReturning] joinPoint.getSignature()={}, return={}", joinPoint.getSignature(), result);
    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn2(JoinPoint joinPoint, Void result) {
        // result 값 변경할수 없음, return하지 않음
        log.info("[AfterReturning] joinPoint.getSignature()={}, return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[AfterThrowing] ex={}, message={}", ex, ex.getMessage());
    }

    @After("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        // 일반적으로 리소스를 해제하는데 사용
        log.info("[After] joinPoint.getSignature()={}", joinPoint.getSignature());
    }
}
