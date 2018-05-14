package com.bjfl.galaxymessage.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "config.properties", encoding = "utf-8")
public class ConfigAuto {

    @Value("${host}")
    public String host;
}
