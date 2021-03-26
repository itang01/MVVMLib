package com.itang.mvvm.exception;

/**
 * Created by itang on 2016/5/23.
 */
public class ResultException extends RuntimeException {

    private int errCode;

    public ResultException() {
    }

    public ResultException(int errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
    }

    public ResultException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ResultException(Throwable throwable) {
        super(throwable);
    }

    public ResultException(String detailMessage) {
        super(detailMessage);
    }

    public int getErrCode() {
        return errCode;
    }
}
