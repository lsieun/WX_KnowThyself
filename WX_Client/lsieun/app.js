//app.js
'use strict';

// 引入工具类库 
import util from './utils/index';

let handler = {
  globalData: {
    windowWidth: 0,
    userInfo: null,
    deviceInfo: {},
    host: "http://www.lsieun.cn:8888/knowthyself"
  },
  urls: {
    user_login: "/user/login"
  },
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    getDeviceInfo(this);

    wx.getSystemInfo({
      success: function (res) {
        this.globalData.windowWidth = res.windowWidth;
        wx.setStorageSync('windowWidth', res.windowWidth)
        console.log(res)
      }.bind(this)
    });

    // 登录
    wx.login({
      success: res => {
        console.log("wx.login==>res==>" + JSON.stringify(res));
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        if (res.code) {
          let code = res.code;
          wx.getUserInfo({//getUserInfo流程
            success: function (res2) {//获取userinfo成功
              console.log("wx.getUserInfo==>res==>" + JSON.stringify(res2));
              let encryptedData = res2.encryptedData;//encodeURIComponent(res2.encryptedData);//一定要把加密串转成URI编码
              let iv = res2.iv;
              //请求自己的服务器
              login(code, encryptedData, iv);
            }
          })
        } else {
          console.log('获取用户登录态失败！' + res.errMsg);
        }
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },

};

App(handler)

function login(code, encryptedData, iv){
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

function getDeviceInfo(app){
  let self = app;
  wx.getSystemInfo({
    success: function (res) {
      self.globalData.deviceInfo = res;
      util.log("self.globalData.deviceInfo==>" + JSON.stringify(self.globalData.deviceInfo));
    }
  })
}
