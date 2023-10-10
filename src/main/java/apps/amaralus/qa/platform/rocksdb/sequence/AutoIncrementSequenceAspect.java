package apps.amaralus.qa.platform.rocksdb.sequence;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AutoIncrementSequenceAspect {

    @Before("execution(public * org.springframework.data.keyvalue.repository.KeyValueRepository.save(..))")
    public void before(JoinPoint joinPoint) {
        log.info("before {}", joinPoint);
    }
}