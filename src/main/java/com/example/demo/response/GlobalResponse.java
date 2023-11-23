package com.example.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class GlobalResponse {

    private String statusCode;
    private String message;
    private Object data;
    private List<String> errors;

}
