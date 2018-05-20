'use strict';

let handler = {
  data: {
    userid: ""
  },
  onLoad: function (options) {
  },
  onShow: function () {
  },
  openAboutApp: function(){
    wx.navigateTo({
      url: '../aboutApp/aboutApp'
    })  
  }
};


Page(handler);
