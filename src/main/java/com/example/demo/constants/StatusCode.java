package com.example.demo.constants;

public enum StatusCode {

    SUCCESS("1000", "success"),
    BAD_REQUEST("2000", "Wrong request format");

    private String code;
    private String message;

    private StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage(){
        return message;
    }


}
