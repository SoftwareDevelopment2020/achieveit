package com.softwaredevelopment.achieveit.utils.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import com.softwaredevelopment.achieveit.utils.JsonUtils;
import com.softwaredevelopment.achieveit.utils.StringHelper;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;

/**
 * 日志记录类
 */
public class Logger {

    private static boolean inited = false;

    /**
     * 访问日志
     */
    private static org.slf4j.Logger visitLog = null;

    /**
     * 信息日志
     */
    private static org.slf4j.Logger infoLog = null;

    /**
     * 错误日志
     */
    private static org.slf4j.Logger errorLog = null;

    public static void init() throws Exception{
        if (inited) {
            return;
        }

        FileSystemResource fsr = new FileSystemResource("config/logback.xml");
        if (fsr.exists()) {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.reset();
            configurator.doConfigure(fsr.getInputStream());
        }

        visitLog = LoggerFactory.getLogger("visit");
        infoLog = LoggerFactory.getLogger("info");
        errorLog = LoggerFactory.getLogger("error");
        inited = true;
    }

    public static void logVisit(Object message) {
        visitLog.info(JsonUtils.toJSONString(message));
    }

    public static void logInfo(String message, Object... args) {
        infoLog.info(getLogEntity(message, args));
    }

    public static void logError(String message, Object... args) {
        errorLog.error(getLogEntity(message, args));
    }

    public static void logError(Throwable e, String message, Object... args) {
        errorLog.error(getLogEntity(message, args), e);
    }

    public static void slf4jLogError(String message, Throwable e) {
        errorLog.error(message, e);
    }

    public static String getLogEntity(String message, Object... args) {
        LogEntity logEntity = new LogEntity();

        // TODO 获取当前用户ID和IP
        // logEntity.setOPER_USER
        // logEntity.setOPER_IP

        logEntity.setMESSAGE(StringHelper.format(message, args));

        return JsonUtils.toJSONString(logEntity, true);
    }

}
