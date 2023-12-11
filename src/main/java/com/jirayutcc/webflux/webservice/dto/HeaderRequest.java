package com.jirayutcc.webflux.webservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@With
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeaderRequest implements Serializable {

    @NotNull(message = "must not null.")
    @NotEmpty(message = "must not empty.")
    private String reqID;

    private String reqChannel;
    private String service;
    private String appVersion;

    @NotNull(message = "must not null.")
    @NotEmpty(message = "must not empty.")
    private String reqDtm;

    private String reqBy;
    private String clientOS;
    private String ip;
    private String txnRefId;
}
