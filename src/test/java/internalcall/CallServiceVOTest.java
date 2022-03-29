package internalcall;

import hello.aop.internalcall.CallServiceV0;
import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceVOTest {

    @Autowired
    CallServiceV0 callServiceVO;

    @Test
    void external() {
        log.info("callServiceVO.getClass()={}", callServiceVO.getClass());
        callServiceVO.external();
    }

    @Test
    void internal() {
        callServiceVO.internal();
    }
}