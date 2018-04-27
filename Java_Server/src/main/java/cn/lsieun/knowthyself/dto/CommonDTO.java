package cn.lsieun.knowthyself.dto;

import cn.lsieun.knowthyself.util.weixin.SignUtil;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.List;

public class CommonDTO {
    private static String MSG_OK = "OK";
    private static String MSG_ERROR = "ERROR";

    private boolean success;
    private String msg;
    private String uid;
    private String timestamp;
    private String nonce;
    private String signature;

    public CommonDTO() {
        success = true;
        msg = MSG_OK;
        uid = null;
        timestamp = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        nonce = String.valueOf(Math.random());
        signature = SignUtil.getSignature(timestamp,nonce);
    }

    public CommonDTO(boolean success, String msg) {
        this();
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
