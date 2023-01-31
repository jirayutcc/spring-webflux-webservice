package com.jirayutcc.webflux.webservice.config;

import com.jirayutcc.webflux.webservice.constant.ErrorMapping;
import com.jirayutcc.webflux.webservice.dto.ErrorResponse;
import com.jirayutcc.webflux.webservice.dto.HeaderResponse;
import com.jirayutcc.webflux.webservice.exception.ClientException;
import com.jirayutcc.webflux.webservice.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandierConfig {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse exception(Exception ex) {
        log.error("Exception : ", ex);
        ErrorResponse resp = new ErrorResponse();
        resp.getHeaderResp().setStatusCd(ErrorMapping.CODE9999.getCode());
        resp.getHeaderResp().setStatusDesc(ErrorMapping.CODE9999.getMessage());

        return resp;
    }

    @ExceptionHandler(value = {ClientException.class})
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse clientException(ClientException ex) {
        log.error("ClientException : ", ex);
        ErrorResponse resp = new ErrorResponse();
        resp.getHeaderResp().setStatusCd(ex.getStatusCd());
        resp.getHeaderResp().setStatusDesc(ex.getStatusDesc());

        return resp;
    }

    @ExceptionHandler(value = {CustomException.class})
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse customException(CustomException ex) {
        log.error("CustomException : ", ex);
        ErrorResponse resp = new ErrorResponse();
        resp.getHeaderResp().setStatusCd(ex.getCode());
        resp.getHeaderResp().setStatusDesc(ex.getMessage());
        resp.getHeaderResp().setErrorDisplay(
                HeaderResponse.ErrorDisplay.builder()
                        .message(ex.getMessage())
                        .title(ex.getDisplayTitle())
                        .build()
        );

        return resp;
    }
}
