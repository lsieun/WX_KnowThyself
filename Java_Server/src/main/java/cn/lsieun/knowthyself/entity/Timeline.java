package cn.lsieun.knowthyself.entity;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import cn.lsieun.knowthyself.util.json.CustomJsonDateDeserializer;

public class Timeline extends CommonEntity{
    private String uid;
    private String userid;
    private String name;
    private String timelineType;
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date startTime;
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date endTime;
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

    public String getTimelineType(){
        return this.timelineType;
    }
    public void setTimelineType(String timelineType){
        this.timelineType = timelineType;
    }

    public Date getStartTime(){
        return this.startTime;
    }
    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getEndTime(){
        return this.endTime;
    }
    public void setEndTime(Date endTime){
        this.endTime = endTime;
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
