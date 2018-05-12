'use strict';

// 引入工具类库 
import util from '../../utils/index.js';

//获取应用实例
var app = getApp();

let handler = {
  data: {
    uid: "",
    userid: "",
    timelineTypeName: ""
  },
  onLoad: function (options) {
    var that = this;

    if (options.uid == undefined) {
      return;
    }

    var uid = options.uid;
    displayTimelineTypeInfo(that, uid);
  },
  onInputName: function (e) {
    this.setData({
      timelineTypeName: e.detail.value
    })
  },
  saveTimelineType: function () {
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
    var timelineTypeName = this.data.timelineTypeName;

    var mydata = {
      "uid": this.data.uid,
      "userid": userid,
      "name": timelineTypeName
    };

    if (!timelineTypeName) {
      wx.showToast({
        title: '名称不能为空',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    var url = app.globalData.host + app.urls.timelinetype_add;
    if (that.data.uid) {
      url = app.globalData.host + app.urls.timelinetype_modify;
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
            wx.navigateBack({
              delta: 1
            })
          }
        });



      }
    })

  }
};

Page(handler);


function displayTimelineTypeInfo(page, uid) {
  var that = page;
  wx.request({
    url: app.globalData.host + app.urls.timelinetype_info,
    data: { "uid": uid },
    method: "POST",
    success: function (res) {
      var flag = res.data.success;

      if (flag == true) {
        var timelineType = res.data.data;
        var uid = timelineType.uid;
        var timelineTypeName = timelineType.name;


        that.setData({
          uid: uid,
          timelineTypeName: timelineTypeName
        });
      }
    }
  });
}
