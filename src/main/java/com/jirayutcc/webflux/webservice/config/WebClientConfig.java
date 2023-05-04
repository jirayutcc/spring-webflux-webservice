package com.jirayutcc.webflux.webservice.config;

import com.jirayutcc.webflux.webservice.properties.WebServiceConfigProperty;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@Slf4j
@Configuration
public class WebClientConfig {

    private final WebServiceConfigProperty webServiceConfigProperty;

    public WebClientConfig(WebServiceConfigProperty webServiceConfigProperty) {
        this.webServiceConfigProperty = webServiceConfigProperty;
    }

    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(getHttpClient()))
                .codecs((configurer) -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
                .build();
    }

    private HttpClient getHttpClient() {
        return getHttpClient(null);
    }

    private HttpClient getHttpClient(SslContext sslContext) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webServiceConfigProperty.getTimeOut() * 1000)
                .responseTimeout(Duration.ofSeconds(webServiceConfigProperty.getTimeOut()))
                .doOnConnected(connection ->
                        connection.addHandlerFirst(new ReadTimeoutHandler(webServiceConfigProperty.getTimeOut(), TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(webServiceConfigProperty.getTimeOut(), TimeUnit.SECONDS)));

        if (null != sslContext)
            httpClient = httpClient.secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));

        return httpClient;
    }
}
