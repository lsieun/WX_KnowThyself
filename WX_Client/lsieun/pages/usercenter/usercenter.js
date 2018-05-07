//获取应用实例
var app = getApp();

let handler = {
  data: {},
  onLoad: function (options){
    if (app.globalData.userInfo){
      this.setData({
        userInfo: app.globalData.userInfo
      })
    }
    else{
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          console.log("app.globalData.userInfo==>" + JSON.stringify(app.globalData.userInfo));
          this.setData({
            userInfo: res.userInfo
          })
        }
      });
    }
  }
};

Page(handler);