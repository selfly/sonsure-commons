package com.sonsure.commons.runtime;

import com.sonsure.commons.exception.SonsureException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 业务拦截器，主要拦截异常及错误信息，最好配合RunBinderOnMvcDestroyInterceptor使用防止内存溢出
 * <p>
 * Created by liyd on 4/9/14.
 */
@Aspect
public class RunBinderInterceptor {

    /**
     * 日志对象
     */
    private static final Logger LOG = LoggerFactory.getLogger(RunBinderInterceptor.class);

    private static ThreadLocal<AtomicInteger> methodHierarchy = new ThreadLocal<AtomicInteger>();

    /**
     * 执行时间超过打印warn日志毫秒数
     */
    private static final long LOG_TIMEOUT = 1000;

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceAnnotation() {

    }

    /**
     * 实际spring中bean id非class名
     */
    @Pointcut("bean(*Service) || bean(*ServiceImpl)")
    public void serviceName() {

    }

    @Pointcut("@within(com.sonsure.commons.runtime.AspectRunBinder)")
    public void aspectRunBinder() {

    }

    @Around("serviceAnnotation() || serviceName() || aspectRunBinder()")
    public Object around(ProceedingJoinPoint pjp) {

        AtomicInteger ai = methodHierarchy.get();
        if (ai == null) {
            ai = new AtomicInteger(1);
            methodHierarchy.set(ai);
        } else {
            ai.incrementAndGet();
        }
        //返回结果
        Object result = null;
        try {
            //此处调用业务方法
            result = pjp.proceed();

        } catch (SonsureException bizException) {
            if (ai.get() > 1) {
                throw bizException;
            }
            RunBinderTransactionAspectSupport.setRollbackOnly();
            RunBinder.addError(bizException);
            //log....
        } catch (Throwable throwable) {
            if (ai.get() > 1) {
                throw new RuntimeException(throwable);
            }
            RunBinderTransactionAspectSupport.setRollbackOnly();
            RunBinder.addError("UN_KNOWN_EXCEPTION", "未知异常");
            //log....
        } finally {
            if (ai.decrementAndGet() == 0) {
                methodHierarchy.remove();
            }
        }
        return result;
    }

    /**
     * 获取基本类型的默认值
     * 如果方法返回的是基本的值类型,直接返回null会出异常
     *
     * @param signature
     * @return
     */
    private Object getDefaultValue(Signature signature) {
        if (!(signature instanceof MethodSignature)) {
            return null;
        }

        MethodSignature methodSignature = (MethodSignature) signature;
        Class<?> returnType = methodSignature.getReturnType();
        if (!returnType.isPrimitive()) {
            return null;
        }
        if (returnType == Boolean.TYPE) {
            return Boolean.FALSE;
        } else if (returnType == Character.TYPE) {
            return '\u0000';
        } else if (returnType == Byte.TYPE) {
            return (byte) 0;
        } else if (returnType == Short.TYPE) {
            return (short) 0;
        } else if (returnType == Integer.TYPE) {
            return 0;
        } else if (returnType == Long.TYPE) {
            return 0L;
        } else if (returnType == Float.TYPE) {
            return 0.0F;
        } else if (returnType == Double.TYPE) {
            return 0.0D;
        }
        return null;
    }

    /**
     * 获取参数字符串
     *
     * @param pjp
     * @return
     */
    private String argsToString(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        return ToStringBuilder.reflectionToString(args, ToStringStyle.MULTI_LINE_STYLE);
        //        if (ArrayUtils.isEmpty(args)) {
        //            return "";
        //        }
        //        StringBuilder sb = new StringBuilder();
        //        for (Object obj : args) {
        //            if (obj == null) {
        //                sb.append("null;");
        //            } else {
        //                sb.append(obj.getClass().getName()).append("=").append(obj).append(";");
        //            }
        //        }
        //        return sb.toString();
    }
}
