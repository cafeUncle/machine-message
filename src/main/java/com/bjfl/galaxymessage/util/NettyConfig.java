package com.bjfl.galaxymessage.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {

    public int port;

    /**
     * 最大线程量
     */
    private static final int maxThreads = 1024;
    /**
     * 数据包最大长度
     */
    private static final int maxFrameLength = 65535;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static int getMaxThreads() {
        return maxThreads;
    }

    public static int getMaxFrameLength() {
        return maxFrameLength;
    }
}
