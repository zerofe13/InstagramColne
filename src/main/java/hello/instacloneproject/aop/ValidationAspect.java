package hello.instacloneproject.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ValidationAspect {



//    @Around("execution(* hello.instacloneproject.controller.*.*(..))")
    public Object Validate(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object[] args = joinPoint.getArgs();
        String requestURI = request.getRequestURI().substring(1);
        for(Object arg: args){
            if( arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;
                if(bindingResult.hasErrors()){
                    log.info("Validate Error={}",bindingResult);
                    log.info("URI={}",requestURI);
                    return requestURI;
                }
            }
        }
        return joinPoint.proceed();
    }
}
