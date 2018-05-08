//获取应用实例
var app = getApp();

let handler = {
  data: {},
  onLoad: function (options){
    var that = this;
    if (app.globalData.userInfo){
      this.setData({
        userInfo: app.globalData.userInfo
      })
    }
    else{
      setTimeout(function(){
        if (app.globalData.userInfo) {
          that.setData({
            userInfo: app.globalData.userInfo
          })
        }
      },2000);
    }
  }
};

Page(handler);


