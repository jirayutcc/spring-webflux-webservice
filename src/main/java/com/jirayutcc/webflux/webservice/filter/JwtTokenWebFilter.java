package com.jirayutcc.webflux.webservice.filter;

import com.jirayutcc.webflux.webservice.config.TokenManager;
import com.jirayutcc.webflux.webservice.utils.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class JwtTokenWebFilter implements Filter {

    @Autowired
    @Qualifier("getTokenManager")
    private TokenManager tokenManager;

    @Autowired
    private JWTHelper jwtHelper;

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String servletPath = httpRequest.getServletPath();
        log.info("URL: {}", servletPath);
        if (!httpRequest.getMethod().equals(HttpMethod.OPTIONS.name())
                && !servletPath.contains("/actuator")
                && !servletPath.contains("/internal")
                && !servletPath.contains("/swagger")
                && !servletPath.contains("/api-docs")
        ) {
            String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
            if (null != token) {
                tokenManager.setJwtPayload(jwtHelper.decodeToken(token));
            }
        }
        chain.doFilter(request, response);
    }
}

