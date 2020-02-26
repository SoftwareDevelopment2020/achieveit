package com.softwaredevelopment.achieveit.asp;

import com.softwaredevelopment.achieveit.http.response.HttpResponse;
import com.softwaredevelopment.achieveit.http.request.BaseRequest;
import com.softwaredevelopment.achieveit.utils.JsonUtils;
import com.softwaredevelopment.achieveit.utils.log.Logger;
import io.swagger.annotations.ApiModelProperty;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 访问日志打印切面
 */
@Aspect
@Component
public class WebLogAsp {

    public WebLogAsp() {

    }

    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.softwaredevelopment.achieveit.controller..*.*(..))")
    public void webLog() {
    }

    /**
     * 进入controller之前打印请求日志并判断必需参数是否有值，无值则返回
     * 对返回数据进行日志打印
     */
    @Around("webLog()")
    public Object doBefore(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        WebRequestLogInfo info = new WebRequestLogInfo();
        Object[] params;

        // 进入controller之前
        // engion
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // 来访者信息
            info.setRemoteAddr(request.getRemoteAddr());
            info.setRemoteHost(request.getRemoteHost());
            info.setRemotePort(request.getRemotePort());

            // 接收者信息
            info.setLocalAddr(request.getRemoteAddr());
            info.setLocalName(request.getRemoteHost());
            info.setLocalPort(request.getRemotePort());

            // 请求信息
            info.setUri(request.getRequestURI());
            info.setHttpMethod(request.getMethod());

            // TODO 获取USERID, 未登录拦截或其他操作？
            // info.setUserId

            info.setClassName(pjp.getSignature().getDeclaringTypeName());
            info.setClassMethodName(pjp.getSignature().getName());

            // 获取参数
            params = pjp.getArgs();
            boolean paramValid = true;
            String message = null;
            if (params != null && params.length > 0) {
                for (Object param : params) {
                    if (param instanceof BaseRequest) {
                        ((BaseRequest) param).setRequestId(info.getRequestId());
                        ((BaseRequest) param).setCurUserId(info.getUserId());
                        ((BaseRequest) param).setCurUserIp(info.getRemoteAddr());
                        ((BaseRequest) param).setCurTime(new Date());
                        if (paramValid) {
                            List<Field> fields = new ArrayList<>();
                            for (Class item = param.getClass(); item != Object.class; item = item.getSuperclass()) {
                                Field[] fs = item.getDeclaredFields();
                                Collections.addAll(fields, fs);
                            }
                            for (Field item : fields) {
                                ApiModelProperty apiModelProperty = item.getAnnotation(ApiModelProperty.class);
                                if (apiModelProperty != null && apiModelProperty.required()) {
                                    if (item.get(param) == null || (item.get(param) instanceof String && (StringUtils.isEmpty(item.get(param))))) {
                                        message = String.format("%s为空", apiModelProperty.value());
                                        paramValid = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    info.addParam(JsonUtils.toJSONString(param));
                }
            }

            // 打印入参
            Logger.logVisit(info);

            if (!paramValid) {
                HttpResponse<String> response = new HttpResponse<>();
                response.setSuccess(false);
                response.setData(message);
                return response;
            }
        } catch (Exception e) {
            Logger.logError(e, "服务器异常");
            return null;
        }
        // end region

        // 进入controller
        Object result = pjp.proceed(params);

        // 进入controller之后
        // region
        try {

            // 输出出参
            long endTime = System.currentTimeMillis();
            WebResponseLogInfo response = new WebResponseLogInfo();
            response.setRequestId(info.getRequestId());
            response.setTimeConsuming(endTime - startTime);
            if (result instanceof HttpResponse) {
                HttpResponse httpResponse = (HttpResponse) result;
                response.setResponseSuccess(httpResponse.isSuccess());
                response.setResponseData(JsonUtils.toJSONString(httpResponse.getData()));
            } else {
                response.setResponseData(JsonUtils.toJSONString(result));
            }
            Logger.logVisit(response);

            return result;
        } catch (Exception e) {
            Logger.logError(e, "服务器异常");
            return null;
        }
        // end region
    }

    /**
     * 异常抛出
     * 打印错误信息
     */
    @AfterThrowing(pointcut = "webLog()", throwing = "throwable")
    public void afterThrowing(Throwable throwable) {
        String message = throwable.getMessage();
        WebResponseLogInfo response = new WebResponseLogInfo();
        response.setResponseSuccess(false);
        response.setResponseData(message);

        Logger.logVisit(response);
    }
}
