package cn.lsieun.knowthyself.dto;

public class UserDTO extends CommonDTO {
    private String wxopenid;
    private String uname;
    private Integer ugender;
    private String uavatar;

    public UserDTO() {
    }

    public UserDTO(boolean success, String msg) {
        super(success, msg);
    }

    public String getWxopenid() {
        return wxopenid;
    }

    public void setWxopenid(String wxopenid) {
        this.wxopenid = wxopenid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getUgender() {
        return ugender;
    }

    public void setUgender(Integer ugender) {
        this.ugender = ugender;
    }

    public String getUavatar() {
        return uavatar;
    }

    public void setUavatar(String uavatar) {
        this.uavatar = uavatar;
    }
}
