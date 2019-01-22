package com.example.demo.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * response 基础类
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 1403218278293175897L;
    private String status = "200";
    private String msg;
    private List<Object> responseBody = new ArrayList<>();

    public BaseResponse() {
    }

    private BaseResponse(String status) {
        this.status = status;
    }

    private BaseResponse(String status, Object responseBody) {
        this.status = status;
        List<Object> responseBodyt = new ArrayList<>();
        responseBodyt.add(responseBody);
        this.responseBody = responseBodyt;
    }

    private BaseResponse(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private BaseResponse(String status, String msg, Object responseBody) {
        this.status = status;
        this.responseBody.add(responseBody);
        this.msg = msg;
    }

    public static BaseResponse createBySuccess() {
        return new BaseResponse(ResponseCode.SUCCESS.getCode());
    }

    public static BaseResponse createBySuccessMessage(String msg) {
        return new BaseResponse(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static BaseResponse createBySuccessMessage(Object data) {
        return new BaseResponse(ResponseCode.SUCCESS.getCode(), data);
    }

    public static BaseResponse createBySuccess(String msg, Object data) {
        return new BaseResponse(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static BaseResponse createByError() {
        return new BaseResponse(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static BaseResponse createByErrorMessage(String errorMessage) {
        return new BaseResponse(ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static BaseResponse createByErrorMessage(String errorCode, String errorMessage) {
        return new BaseResponse(errorCode, errorMessage);
    }

    @Override
    public String toString() {
        return "[status=" + status + ", data=" + responseBody + "]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getResponseBody() {
        if (responseBody == null) {
            responseBody = new ArrayList<>();
        }
        return responseBody;
    }

    public void setResponseBody(List<Object> responseBody) {
        this.responseBody = responseBody;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
