'use strict';

import config from '../../utils/config'

//获取应用实例
var app = getApp();

let handler = {
  data: {
    userid: "",
    username: "",
    avatar: "",
    genderIndex: 0,
    genderNames: ["保密", "男", "女"],
    defaultPortrait: ""
  },
  onLoad: function (options) {
    this.setData({
      defaultPortrait: config.defaultImg.portraitImg
    });
  },
  onShow: function () {
    var that = this;
    displayUserInfo(that);
  },
  copyUserId: function (e) {
    var self = this;
    wx.setClipboardData({
      data: self.data.userid,
      success: function (res) {
        wx.showToast({
          title: '复制成功',
          icon: 'success',
          duration: 1000
        });
      }
    });
  },
  updateUserInfo: function (e) {
    console.log("updateUserInfo ==> " + JSON.stringify(e));
    var that = this;
    wx.checkSession({
      success: function () {
        //session_key 未过期，并且在本生命周期一直有效
        wx.getUserInfo({//getUserInfo流程
          success: function (res) {//获取userinfo成功
            console.log("wx.getUserInfo==>res==>" + JSON.stringify(res));
            let encryptedData = res.encryptedData;//encodeURIComponent(res2.encryptedData);//一定要把加密串转成URI编码
            let iv = res.iv;
            //请求自己的服务器
            //login(code, encryptedData, iv);
            updateServerUserInfo(that.data.userid, encryptedData, iv);
          }
        })
      },
      fail: function () {
        // session_key 已经失效，需要重新执行登录流程
        wx.showToast({
          title: '登录状态已经过期，请退出小程序重新登录',
          icon: 'none',
          duration: 3000
        });

      },
      complete: function(){
        console.log("wx.checkSession complete");
      }
    })
  }
};

Page(handler);

function updateServerUserInfo(userid,encryptedData, iv){
  console.log('encryptedData=' + encryptedData + '&iv=' + iv);
  //创建一个dialog
  wx.showToast({
    title: '同步用户信息...',
    icon: 'loading',
    duration: 10000
  });

  //请求服务器
  wx.request({
    url: app.globalData.host + app.urls.user_update,
    data: {
      uid: userid,
      encryptedData: encryptedData,
      iv: iv
    },
    method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
    header: {
      'content-type': 'application/json'
    }, // 设置请求的 header
    success: function (res) {
      // success
      wx.hideToast();
      console.log('服务器返回' + JSON.stringify(res.data));
      var flag = res.data.success;
      if(flag == true){
        app.wxLogin();
        wx.showToast({
          title: '同步成功!退出小程序，重新进入，即可查看更新后的信息。',
          icon: 'none',
          duration: 3000
        });
      }
      else{
        wx.showToast({
          title: '同步失败!',
          icon: 'none',
          duration: 3000
        });
      }

    },
    fail: function () {
      // fail
      wx.hideToast();
      wx.showToast({
        title: '通信失败!',
        icon: 'none',
        duration: 3000
      });
    },
    complete: function () {
      // complete
      //wx.hideToast();
    }
  });
}

function displayUserInfo(page) {
  if (app.globalData.userInfo) {
    var userid = app.globalData.userInfo.uid;
    var username = app.globalData.userInfo.uname;
    var gender = app.globalData.userInfo.ugender;
    var avatar = app.globalData.userInfo.uavatar;
    console.log("avatar = " + avatar);
    page.setData({
      userid: userid,
      username: username,
      genderIndex: gender,
      avatar: avatar
    })
    console.log("page.data.avatar = " + page.data.avatar);
  }
  else {
    setTimeout(function () {
      displayUserInfo(page);
    }, 2000);
  }
}