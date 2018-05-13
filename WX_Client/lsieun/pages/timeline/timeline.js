//timeline.js
'use strict';

// 引入工具类库 
import util from '../../utils/index.js';

//获取应用实例
var app = getApp();

let handler = {
  data: {
    currentDay:"",
    totalTime:"",
    timelineList: [],
    userAvatarParentAnimation: {},  // 用户头像父对象的变换
    userAvatarAnimation: {},        // 用户头像父对象的变换
    userAvatarTouchParams: {}      // 用户头像的touch参数
  },
  onLoad: function (options) {
    var that = this;
    var currentDay = util.getToday();
    var totalTime = "00时00分";
    this.setData({
      currentDay: currentDay,
      totalTime: totalTime
    });
  },
  onShow: function() {
    this.resetPageAnimation();
    displayTimelineList(this);
  },

  //事件处理函数
  userAvatarTap: function (e) {
    var that = this;
    var userAvatarAnimation = getUserAvatar360Animation();
    this.setData({
      userAvatarAnimation: userAvatarAnimation.export()
    })
    if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
      wx.showToast({
        title: '您好像没有登录哎~~~',
        icon: 'none',
        duration: 3000,
        success: function(){
          
        }
      });
      setTimeout(() => {
        that.resetPageAnimation();
      }, 1500);
      return;
    }
    setTimeout(() => {
      wx.navigateTo({
        url: '/pages/timerecord/timerecord'
      })
    }, 1000);
  },
  editTimeline: function (e) {
    var that = this;

    var uid = e.target.dataset.uid;

    wx.navigateTo({
      url: '/pages/timerecord/timerecord?uid=' + uid
    });
  },
  resetPageAnimation: function(){
    var userAvatarParentAnimation = getUserAvatarParentOriginAnimation();
    var userAvatarAnimation = getUserAvatarOriginAnimation();

    this.setData({
      userAvatarParentAnimation: userAvatarParentAnimation.export(),
      userAvatarAnimation: userAvatarAnimation.export()
    });
  }


}

Page(handler);

function displayTimelineList(page){
  var that = page;
  if (app.globalData.userInfo && app.globalData.userInfo.uid) {
    var userid = app.globalData.userInfo.uid;
    var currentDay = util.getToday();
    refreshTimelineList(page, userid, currentDay);
  }
  else {
    setTimeout(function () {
      displayTimelineList(page);
    }, 2000);
  }  
}

function refreshTimelineList(page, userid, currentDay){
  var that = page;

  wx.request({
    url: app.globalData.host + app.urls.timeline_daylist,
    data: {
      "userid": userid,
      "currentDay": currentDay,
      "firstResult": "0",
      "maxResult": "200",
      "order": "desc"
    },
    method: "POST",
    success: function (res) {
      let flag = res.data.success;
      console.log("task==>" + JSON.stringify(res.data));
      if (flag != true) {
        var toastText = "获取数据失败" + res.data.msg;
        wx.showToast({
          title: toastText,
          icon: 'none',
          duration: 2000
        });
        return;
      }
      var timelineList = res.data.dataList;
      var totalTime = "00时00分";
      var totalMinutes =  0;
      if (timelineList && timelineList.length > 0){
        for (var i = 0; i < timelineList.length;i++){
          var timeline = timelineList[i];
          var startTime = currentDay + " " + timeline.startTime + ":00";
          var endTime = currentDay + " " + timeline.endTime + ":00";
          var startDate = util.getDateByStr(startTime);
          var endDate = util.getDateByStr(endTime);
          console.log("startDate = " + startTime);
          console.log("endDate = " + endTime);
          var diffMinutes = parseInt(endDate - startDate) / 1000 / 60;
          timeline.name = timeline.name + "(" + diffMinutes + "分钟)";
          totalMinutes += diffMinutes;
        }

        var minutes = totalMinutes % 60;
        var hours = parseInt(totalMinutes / 60);
        minutes = ('0' + minutes).slice(-2);
        hours = ('0' + hours).slice(-2);

        totalTime = hours + "时" + minutes +"分";
      }
      that.setData({
        currentDay: currentDay,
        totalTime: totalTime,
        timelineList: timelineList
      });
    }
  });
}


function getUserAvatarOriginAnimation() {
  var userAvatarAnimation = wx.createAnimation({
    duration: 1000,
    timingFunction: 'ease'
  });
  userAvatarAnimation.rotate(0).step();
  return userAvatarAnimation;
}

function getUserAvatar360Animation() {
  var userAvatarAnimation = wx.createAnimation({
    duration: 1000,
    timingFunction: 'ease'
  });
  userAvatarAnimation.rotate(360).step();
  return userAvatarAnimation;
}

function getUserAvatarParentOriginAnimation() {
  var userAvatarParentAnimation = wx.createAnimation({
    duration: 500,
    timingFunction: 'ease',
  });
  userAvatarParentAnimation.translate(-35, 0).step();
  return userAvatarParentAnimation;
}