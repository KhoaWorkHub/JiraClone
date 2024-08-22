package com.khoa.managementsystem.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta {
    private String requestId;

    private Integer code = 200;

    private String message;

    private String httpCode;


    public Meta(String requestId, Integer code, String message, String httpCode) {
        this.requestId = requestId;
        this.code = (code != null) ? code : 200; // Luôn đảm bảo code không bị null
        this.message = message;
        this.httpCode = httpCode;
    }

}
