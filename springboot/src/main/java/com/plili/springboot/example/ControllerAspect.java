package com.plili.springboot.example;



import com.plili.springboot.example.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.LoginException;
import java.lang.reflect.Method;

/**
 * @author: lipan
 * @date: 2019-06-13
 * @description:
 *
 * 我们用springboot做后台开发，难免会用到权限校验，比如查看当前用户是否合法，是否是管理员。而spring的面向切面的特效可以帮助我们很好的实现动态的权限校验。这里我们就用到的spring的aop。接下来就带领大家用aop和注解来快速的实现权限校验
 */
@Aspect
@Component
public class ControllerAspect {
    //日志
    private final static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Autowired
    private UserService userService;


    /**
     * 定义切点
     * @Pointcut("execution(public * com.plili.learnAnnotation.example.controller.*.*(..))")
     *  @Pointcut("@annotation(permission)")
     */
    @Pointcut("@annotation(permission)")
    public void privilege(Permission permission) {

    }

    /**
     * 权限环绕通知
     *
     * @param joinPoint
     * @throws Throwable
     */
    @ResponseBody
    @Around("privilege(permission)")
   // @Before("privilege(permission)")
    public Object isAccessMethod(ProceedingJoinPoint joinPoint,Permission permission) throws Throwable{
        System.out.println("==========");

        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();  //这里获取的只有一个目标方法？


        //得到方法的访问权限
      //  final String methodAccess = AnnotationParse.privilegeParse(targetMethod);

        //如果该方法上没有权限注解，直接调用目标方法
        if (!targetMethod.isAnnotationPresent( Permission.class )) {  //要不要加！
            return joinPoint.proceed();
        } else {
            //获取当前用户
            Object[] args = joinPoint.getArgs();
            if (args == null) {
                throw new LoginException("参数错误");
            }
            String currentUser = args[0].toString();
            logger.info("访问用户，{}", currentUser);
            if (!userService.isAdmin(currentUser)) {
                throw new LoginException("您不是管理员");
            } else {
                logger.info("您是管理员");
                //是管理员时，才返回所需要的信息
                return joinPoint.proceed();
            }

        }

    }

}
