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
    user_login: "/user/login"
  },
  onLaunch: function () {
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

};

App(handler)

function login(app, wx_code){
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
      if(result.success == true){
        app.globalData.userInfo = result.data;
      }
      else{
        util.alert("提示","用户登录失败")
      }

    },
    fail: function () {
      // fail
      // wx.hideToast();
      util.alter("提示", "通信失败！")
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
