package com.bjfl.galaxymessage.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("config.properties")
public class ConfigAuto {

    @Value("${host}")
    public String host;
}
