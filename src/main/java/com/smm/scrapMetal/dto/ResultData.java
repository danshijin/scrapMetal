package com.smm.scrapMetal.dto;

import java.io.Serializable;

public class ResultData implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3692543113503847598L;

    /**
     * true:成功， false:失败
     */
    private boolean           success;

    /**
     * 错误码
     */
    private String            errorcode;
    /**
     * 错误信息
     */
    private String            errMsg;

    /**
     * 如果为true则需登录，请求失败，默认False
     */
    private boolean           unAuthorizedRequest;

    /**
     * 返回数据
     */
    private String            result;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public boolean isUnAuthorizedRequest() {
        return unAuthorizedRequest;
    }

    public void setUnAuthorizedRequest(boolean unAuthorizedRequest) {
        this.unAuthorizedRequest = unAuthorizedRequest;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultData [success=" + success + ", errorcode=" + errorcode + ", errMsg=" + errMsg
                + ", unAuthorizedRequest=" + unAuthorizedRequest + ", result=" + result + "]";
    }

}
