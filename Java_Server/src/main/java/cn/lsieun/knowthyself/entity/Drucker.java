package cn.lsieun.knowthyself.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import cn.lsieun.knowthyself.util.json.CustomJsonDateDeserializer;

public class Drucker extends CommonEntity{

    private String uid;
    private String userid;
    private String title;
    private String author;
    private String url;
    private String desc;
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date pubTime;
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

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return this.author;
    }
    public void setAuthor(String author){
        this.author = author;
    }

    public String getUrl(){
        return this.url;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public String getDesc(){
        return this.desc;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }

    public Date getPubTime(){
        return this.pubTime;
    }
    public void setPubTime(Date pubTime){
        this.pubTime = pubTime;
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
