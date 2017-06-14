package com.arity.a6;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties
@PropertySource("classpath:jmeter.yml")
@Configuration
@Getter
@Setter
public class JmeterPropertyLoader {
    private String jmeterProperties;
    private String jmeterHome;
    private String jmeterJmx;
}
