package cn.lsieun.knowthyself.dto;

public class ValidationDTO {
    private boolean success;
    private String errMsg;

    public ValidationDTO(boolean success, String errMsg) {
        this.success = success;
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
