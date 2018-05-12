package cn.lsieun.knowthyself.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimelineDTO extends CommonDTO {
    private String uid;
    private String userid;
    private String name;
    private String timelineType;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "yyyy-MM-dd")
    private Date currentDay;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "HH:mm")
    private Date startTime;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern = "HH:mm")
    private Date endTime;

    public Date getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Date currentDay) {
        this.currentDay = currentDay;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimelineType() {
        return timelineType;
    }

    public void setTimelineType(String timelineType) {
        this.timelineType = timelineType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
