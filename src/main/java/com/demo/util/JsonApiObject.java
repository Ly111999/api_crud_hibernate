package com.demo.util;

public class JsonApiObject {
    private String message;
    private int status;
    private Object data;

    public JsonApiObject() {
    }

    public JsonApiObject(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public JsonApiObject(String message, int status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
