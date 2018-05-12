'use strict';

// 引入工具类库 
import util from '../../utils/index.js';

//获取应用实例
var app = getApp();

let handler = {
  data:{
    userid: "",
    timelineTypeList: null
  },
  onLoad: function (options) {
    // var that = this;
    // displayTimelineTypeList(that);
  },
  onShow: function () {
    // 页面显示
    var that = this;
    displayTimelineTypeList(that);
  },  
  editTimelineType: function(e){
    var uid = e.currentTarget.dataset.taskid;
    wx.navigateTo({
      url: '../timelinetype/timelinetype?uid=' + uid
    })
  }
};

Page(handler);

function displayTimelineTypeList(page){
  if (app.globalData.userInfo && app.globalData.userInfo.uid) {
    var userid = app.globalData.userInfo.uid;
    page.setData({
      userid: userid
    });
    refreshTimelineTypeList(page, userid);
  }
  else {
    setTimeout(function () {
      displayTaskList(page);
    }, 2000);
  }
}

function refreshTimelineTypeList(page, userid){
  var that = page;

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
      if (!timelineTypeList) {
        that.setData({
          timelineTypeList: null
        });
      }
      else{
        that.setData({
          timelineTypeList: timelineTypeList
        });
      }
    }
  });
}