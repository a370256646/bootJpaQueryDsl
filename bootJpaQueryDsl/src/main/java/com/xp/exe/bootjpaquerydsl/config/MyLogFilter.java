package com.xp.exe.bootjpaquerydsl.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @description: 自定义logback日志过滤器
 * @author: coderXp
 * @date: 2023年06月25日
 * @version: 1.0.0
 */
public class MyLogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        // 过滤日志内容中含有ResultSet. Connection. PreparedStatement.的内容
        String loggerMsg = iLoggingEvent.getMessage();
        return (loggerMsg.contains("ResultSet.")
                || loggerMsg.contains("Connection.")
                || loggerMsg.contains("PreparedStatement.")) ? FilterReply.DENY : FilterReply.ACCEPT;
    }
}
