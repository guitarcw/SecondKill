package org.seckill.dto;

public class SeckillResult<T> {
    private boolean seccess;

    private T data;

    private String error;

    public SeckillResult(boolean seccess, T data) {
        this.seccess = seccess;
        this.data = data;
    }

    public SeckillResult(boolean seccess, String error) {
        this.seccess = seccess;
        this.error = error;
    }

    public boolean isSeccess() {
        return seccess;
    }

    public void setSeccess(boolean seccess) {
        this.seccess = seccess;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
