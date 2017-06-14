package com.arity.a6;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            ((TomcatEmbeddedServletContainerFactory) container)
                .addConnectorCustomizers(new TomcatConnectorCustomizer() {
                @Override
                public void customize(Connector connector) {
                    ProtocolHandler protocolHandler = connector.getProtocolHandler();
                    Http11NioProtocol handler = (Http11NioProtocol) protocolHandler;

                    handler.setBacklog(15);
                    handler.setMaxConnections(1000);
                    handler.setAcceptorThreadCount(2);
                    handler.setMaxThreads(50);
                    handler.setMinSpareThreads(handler.getMaxThreads());
                    handler.setMaxKeepAliveRequests(0);
                    handler.setConnectionTimeout(3000);
                    handler.setKeepAliveTimeout(3000);
                }
            });
        };
    }
}

