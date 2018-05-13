'use strict';

// 引入工具类库 
import util from './utils/index';

let handler = {
  globalData: {
    userInfo: null,
    deviceInfo: {},
    host: "http://www.lsieun.cn:8888/knowthyself"
  },
  urls: {
    user_login: "/user/login",
    user_update: "/user/update",
    user_info: "/user/info",
    task_daylist: "/task/daylist",
    task_done: "/task/done",
    task_undo: "/task/undo",
    task_del: "/task/del",
    task_add: "/task/add",
    task_modify: "/task/modify",
    task_info: "/task/info",
    task_all:"/task/all",
    timelinetype_add: "/timelinetype/add",
    timelinetype_modify: "/timelinetype/modify",
    timelinetype_list: "/timelinetype/list",
    timelinetype_info: "/timelinetype/info",
    timeline_info: "/timeline/info",
    timeline_add: "/timeline/add",
    timeline_modify:"/timeline/modify",
    timeline_daylist: "/timeline/daylist",
    timestat_day: "/timestat/day",
    timestat_week: "/timestat/week",
    timestat_month: "/timestat/month",
    drucker_list: "/drucker/list"
  },
  onLaunch: function () {
    this.wxLogin();
  },
  wxLogin: function () {
    var self = this;
    // 登录
    wx.login({
      success: res => {
        console.log("wx.login==>res==>" + JSON.stringify(res));
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        if (res.code) {
          var wx_code = res.code;
          login(self, wx_code);
        } else {
          console.log('获取用户登录态失败！' + res.errMsg);
        }
      }
    })
  },
  wxRefresh: function () {
    refreshUserInfo(this);
  }
};

App(handler)

function refreshUserInfo(app) {
  //请求服务器
  wx.request({
    url: app.globalData.host + app.urls.user_info,
    data: {
      code: app.globalData.userInfo.uid
    },
    method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
    header: {
      'content-type': 'application/json'
    }, // 设置请求的 header
    success: function (res) {
      // success
      console.log('refreshUserInfo==>: ' + JSON.stringify(res.data));
      var result = res.data;
      if (result.success == true) {
        app.globalData.userInfo = result.data;
      }
      else {
        util.alert("提示", "获取用户信息失败")
      }

    },
    fail: function () {
      // fail
      wx.showModal({
        content: '通信失败！',
        showCancel: false,
        success: function (res) {
          if (res.confirm) {
            console.log('通信失败！')
          }
        }
      });
    },
    complete: function () {
      // complete
    }
  });
}

function login(app, wx_code) {
  //创建一个dialog
  wx.showToast({
    title: '正在登录...',
    icon: 'loading',
    duration: 10000
  });

  //请求服务器
  wx.request({
    url: app.globalData.host + app.urls.user_login,
    data: {
      code: wx_code
    },
    method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
    header: {
      'content-type': 'application/json'
    }, // 设置请求的 header
    success: function (res) {
      // success
      //wx.hideToast();
      console.log('服务器返回: ' + JSON.stringify(res.data));
      var result = res.data;
      if (result.success == true) {
        app.globalData.userInfo = result.data;
      }
      else {
        util.alert("提示", "用户登录失败")
      }

    },
    fail: function () {
      // fail
      // wx.hideToast();
      wx.showModal({
        content: '登录失败:无法连接到服务器！',
        showCancel: false
      });
    },
    complete: function () {
      // complete
      wx.hideToast();
    }
  });
}

function recordLogs() {
  // 展示本地存储能力
  var logs = wx.getStorageSync('logs') || []
  logs.unshift(Date.now())
  wx.setStorageSync('logs', logs)
}

function loginaaa(code, encryptedData, iv) {
  console.log('code=' + code + '&encryptedData=' + encryptedData + '&iv=' + iv);
  //创建一个dialog
  wx.showToast({
    title: '正在登录...',
    icon: 'loading',
    duration: 10000
  });

  //请求服务器
  wx.request({
    url: handler.globalData.host + handler.urls.user_login,
    data: {
      code: code,
      encryptedData: encryptedData,
      iv: iv
    },
    method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
    header: {
      'content-type': 'application/json'
    }, // 设置请求的 header
    success: function (res) {
      // success
      //wx.hideToast();
      console.log('服务器返回' + res.data);

    },
    fail: function () {
      // fail
      // wx.hideToast();
    },
    complete: function () {
      // complete
      wx.hideToast();
    }
  });
}

function getDeviceInfo(app) {
  let self = app;
  wx.getSystemInfo({
    success: function (res) {
      self.globalData.deviceInfo = res;
      util.log("self.globalData.deviceInfo==>" + JSON.stringify(self.globalData.deviceInfo));
    }
  })
}
