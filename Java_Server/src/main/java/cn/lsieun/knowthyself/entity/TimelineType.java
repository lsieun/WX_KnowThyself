package cn.lsieun.knowthyself.entity;

import java.util.Date;

public class TimelineType {
    private String uid;
    private String userid;
    private String name;
    private Date createTime;
    private Date lastEditTime;
    private Integer isValid;

    public String getUid(){
        return this.uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }

    public String getUserid(){
        return this.userid;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
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
}
