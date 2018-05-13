'use strict';

// 引入工具类库 
import util from '../../utils/index.js';

//获取应用实例
var app = getApp();

let handler = {
  data: {
    uid:undefined,
    timelineName: "",
    typeIndex: 0,
    typeIds:[],
    typeNames: [],
    currentDay: "2018-04-01",
    startTime: "12:00",
    endTime: "12:30",    
    pickeStartDate: "2018-01-01",
    pickerEndDate: "2038-01-31",
    pickerStartTime: "00:00",
    pickerEndTime: "23:59"
  },
  onLoad: function (options) {
    var that = this;
    let now = new Date();

    let todayFormat = util.getyyMMdd(now);

    let startTimeFormat = util.gethhmm(now);

    let newDate = util.getDateByAddMinutes(now, 30);
    let todayMaxTime = util.getTodayMaxTime(now);
    if(newDate.getTime() > todayMaxTime){
      newDate.setTime(todayMaxTime);
    }

    let endTimeFormat = util.gethhmm(newDate);
    
    this.setData({
      currentDay: todayFormat,
      startTime: startTimeFormat,
      endTime: endTimeFormat
    });
    
    // if (options.uid == undefined) {
    //   return;
    // }

    var uid = options.uid;
    displayTimelineInfo(that, uid);
  },
  onInputName: function (e) {
    this.setData({
      timelineName: e.detail.value
    })
  },
  bindTypeChange: function (e) {
    this.setData({
      typeIndex: e.detail.value
    });
  },
  bindCurrentDateChange: function (e) {
    this.setData({
      currentDay: e.detail.value
    });
  },
  bindStartTimeChange: function (e) {
    this.setData({
      startTime: e.detail.value
    })
  },
  bindEndTimeChange: function (e) {
    this.setData({
      endTime: e.detail.value
    })
  },
  saveTimeline: function(){
    var that = this;

    if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
      wx.showToast({
        title: '您好像没有登录哎~~~',
        icon: 'none',
        duration: 3000
      });
      return;
    }

    var userid = app.globalData.userInfo.uid;
    var timelineName = this.data.timelineName;
    var timelineType = this.data.typeIds[this.data.typeIndex];
    var currentDay = this.data.currentDay;
    var startTime = currentDay + " " + this.data.startTime + ":00";
    var endTime = currentDay + " " + this.data.endTime + ":00";

    var mydata = {
      "uid": this.data.uid,
      "userid": userid,
      "name": timelineName,
      "timelineType": timelineType,
      "startTime": startTime,
      "endTime": endTime
    };

    if (!timelineName) {
      wx.showToast({
        title: '事项名称不能为空',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    var startDate = util.getDateByStr(startTime);
    var endDate = util.getDateByStr(endTime);

    if (endDate < startDate) {
      wx.showToast({
        title: '"结束时间"不能小于"开始时间"',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    var url = app.globalData.host + app.urls.timeline_add;
    if (that.data.uid) {
      url = app.globalData.host + app.urls.timeline_modify;
    }

    wx.request({
      url: url,
      data: mydata,
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {

        var flag = res.data.success;
        var toastText = "操作成功!";
        if (flag != true) {
          toastText = "操作失败 " + res.data.msg;
        }

        wx.showToast({
          title: toastText,
          icon: 'none',
          duration: 2000,
          success: function () {
            if (that.data.taskid == undefined && flag) {
              console.log("添加任务成功");
              wx.navigateBack({
                delta: 1
              })
            }
          }
        });



      }
    })

  }
}

Page(handler);

function displayTimelineInfo(page, uid){
  var that = page;

  if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
    wx.showToast({
      title: '您好像没有登录哎~~~',
      icon: 'none',
      duration: 3000
    });
    return;
  } 

  var userid = app.globalData.userInfo.uid;

  wx.request({
    url: app.globalData.host + app.urls.timelinetype_list,
    data: {
      "userid": userid,
      "firstResult": "0",
      "maxResult": "200",
      "order": "asc"
    },
    method: "POST",
    success: function (res) {
      let flag = res.data.success;

      if (flag != true) {
        var toastText = "获取数据失败" + res.data.msg;
        wx.showToast({
          title: toastText,
          icon: 'none',
          duration: 2000
        });
        return;
      }
      var timelineTypeList = res.data.dataList;
      if (!timelineTypeList || timelineTypeList.length < 1) {
        that.setData({
          typeIds: [0],
          typeNames: ["未分类"]
        });
      }
      else {
        var typeIds = [];
        var typeNames = [];

        for (var i = 0; i < timelineTypeList.length; i++){
          var item = timelineTypeList[i];
          typeIds[i] = item.uid;
          typeNames[i] = item.name;
        }

        that.setData({
          typeIds: typeIds,
          typeNames: typeNames
        });
      }

      if(uid != undefined){
        that.setData({
          uid: uid
        });
        wx.request({
          url: app.globalData.host + app.urls.timeline_info,
          data: { "uid": uid },
          method: "POST",
          success: function (res) {
            var flag = res.data.success;

            if (flag == true) {
              var timeline = res.data.data;
              var timelineid = timeline.uid;
              var timelineName = timeline.name;
              var timelineType = timeline.timelineType;
              var currentDay = timeline.currentDay;
              var startTime = timeline.startTime;
              var endTime = timeline.endTime;

              var typeIndex = 0;
              var typeIds = that.data.typeIds;
              if (typeIds && typeIds.length > 0){
                for (var i = 1; i < typeIds.length;i++){
                  var typeId = typeIds[i];
                  if (typeId == timelineType){
                    typeIndex = i;
                    break;
                  }
                }
              }

              that.setData({
                uid: timelineid,
                timelineName: timelineName,
                typeIndex: typeIndex,
                currentDay: currentDay,
                startTime: startTime,
                endTime: endTime
              });
            }
          }
        });
      }
    }
  });
}


