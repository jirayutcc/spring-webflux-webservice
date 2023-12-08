package com.jirayutcc.webflux.webservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationPropertiesScan
@ConfigurationProperties("web-service")
public class WebServiceConfigProperty {

    private int timeOut;
    private SpotifyUrl spotifyUrl;


    @Data
    public static class SpotifyUrl {


    }
}
