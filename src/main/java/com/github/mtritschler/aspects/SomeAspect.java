package com.github.mtritschler.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class SomeAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SomeAspect.class);

    @AfterReturning(pointcut = "execution(* com.github.mtritschler.aspects.BankImpl.getAccounts(..))", returning = "returnValue")
    public Map<String, String> logCallee(Map<String, String> returnValue) {
        LOGGER.info("Result is {}", returnValue);
        return returnValue;
    }

}
