package cn.lsieun.knowthyself.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User extends CommonEntity{

    private String uid;
    private String wxopenid;
    private String uname;
    private Integer ugender;
    private String uavatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastEditTime;
    private Integer isValid;

    public String getUid(){
        return this.uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }

    public String getWxopenid(){
        return this.wxopenid;
    }
    public void setWxopenid(String wxopenid){
        this.wxopenid = wxopenid;
    }

    public String getUname(){
        return this.uname;
    }
    public void setUname(String uname){
        this.uname = uname;
    }

    public Integer getUgender(){
        return this.ugender;
    }
    public void setUgender(Integer ugender){
        this.ugender = ugender;
    }

    public String getUavatar(){
        return this.uavatar;
    }
    public void setUavatar(String uavatar){
        this.uavatar = uavatar;
    }

    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public Date getLastEditTime(){
        return this.lastEditTime;
    }
    public void setLastEditTime(Date lastEditTime){
        this.lastEditTime = lastEditTime;
    }

    public Integer getIsValid(){
        return this.isValid;
    }
    public void setIsValid(Integer isValid){
        this.isValid = isValid;
    }

    /*微信的相关字段*/
    public String  code;
    private String encryptedData;
    private String iv;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}

