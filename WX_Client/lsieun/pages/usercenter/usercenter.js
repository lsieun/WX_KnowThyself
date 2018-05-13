'use strict';

import config from '../../utils/config'

//获取应用实例
var app = getApp();

let handler = {
  data: {
    defaultPortrait: ""
  },
  onLoad: function (options){
    this.setData({
      defaultPortrait: config.defaultImg.portraitImg
    });
  },
  onShow: function () {
    var that = this;
    displayUserInfo(that);
  },
  catUserInfo: function(){
    if (!app.globalData.userInfo || !app.globalData.userInfo.uid){
      return;
    }
    wx.navigateTo({
      url: '../userinfo/userinfo'
    })
  },
  openTimelineType: function(){
    if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
      return;
    }
    wx.navigateTo({
      url: '../timelinetypelist/timelinetypelist'
    })    
  },
  openTimeDayStatistics: function(){
    if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
      return;
    }
    wx.navigateTo({
      url: '../timestatsday/timestatsday'
    })     
  },
  openTimeWeekStatistics: function () {
    if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
      return;
    }
    wx.navigateTo({
      url: '../timestatsweek/timestatsweek'
    })
  },
  openTimeMonthStatistics: function () {
    if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
      return;
    }
    wx.navigateTo({
      url: '../timestatsmonth/timestatsmonth'
    })
  }    
};

Page(handler);

function displayUserInfo(page){
  if (app.globalData.userInfo) {
    page.setData({
      userInfo: app.globalData.userInfo
    })
  }
  else{
    setTimeout(function(){
      displayUserInfo(page);
    },2000);
  }
}
