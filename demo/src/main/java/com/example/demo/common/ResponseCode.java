package com.example.demo.common;

public enum ResponseCode {
    SUCCESS("200","SUCCESS"),
    ERROR("1","ERROR"),
    NULLERROR("EP_ERROR_MSG","argument is required, it must not be null"),
    DB_ERROR("DBERROR","THE DB ERROR"),
    UPLODAFAIL("UPLODAFAIL","upload fail please try again"),
    FILE_PATH_ERROR("FILE_PATH_ERROR","file path not complete or wrong");

    private final String code;
    private final String desc;

    ResponseCode(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
