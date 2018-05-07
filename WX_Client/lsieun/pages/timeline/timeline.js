//@ sourceURL=timeline.js

//获取应用实例
var app = getApp();

let pageOptions = {
  data: {
    userAvatarParentAnimation: {},  // 用户头像父对象的变换
    userAvatarAnimation: {},        // 用户头像父对象的变换
    userAvatarTouchParams: {}      // 用户头像的touch参数
  },
  onLoad: function (options) {
    if (app.globalData.userInfo) {
      console.log("if==>" + app.globalData.userInfo)
      this.setData({
        userInfo: app.globalData.userInfo
      })
    } else {
      console.log("else==>" + app.globalData.userInfo) //FIXME: 这里总是获取信息
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      });
    }
  },
  onShow: function() {
    this.resetPageAnimation();
  },

  //事件处理函数
  userAvatarTap: function (e) {
    var userAvatarAnimation = getUserAvatar360Animation();
    this.setData({
      userAvatarAnimation: userAvatarAnimation.export()
    })
    setTimeout(() => {
      wx.navigateTo({
        url: '/pages/timerecord/timerecord'
      })
    }, 50);
  },
  userAvatarTouchStart: function (e) {
    var startX = e.touches[0].clientX;
    this.setData({
      userAvatarTouchParams: Object.assign({}, this.data.userAvatarTouchParams, { startX: startX })
    });
    console.log(this.data.userAvatarTouchParams);
  },
  userAvatarTouchMove: function (e) {
    var currentX = e.touches[0].clientX;

    var diff = currentX - this.data.userAvatarTouchParams.startX;
    console.log("diff===>" + diff);
    if (diff > 0.3 * app.globalData.windowWidth) {
      wx.navigateTo({
        url: '/pages/timelinestats/timelinestats'
      })
    } else {
      var left = -35 + diff;
      var userAvatarParentAnimation = wx.createAnimation({
        duration: 20,
        timingFunction: 'linear'
      })
      var userAvatarAnimation = wx.createAnimation({
        duration: 20,
        timingFunction: 'linear'
      })
      userAvatarParentAnimation.translate(left, 0).step();
      userAvatarAnimation.rotate(diff).step();
      this.setData({
        userAvatarParentAnimation: userAvatarParentAnimation.export(),
        userAvatarAnimation: userAvatarAnimation.export()
      })
    }
  },
  userAvatarTouchEnd: function (e) {
    console.log('userAvatarTouchEnd')
    this.resetPageAnimation();
  },
  resetPageAnimation: function(){
    var userAvatarParentAnimation = getUserAvatarParentOriginAnimation();
    var userAvatarAnimation = getUserAvatarOriginAnimation();

    this.setData({
      userAvatarParentAnimation: userAvatarParentAnimation.export(),
      userAvatarAnimation: userAvatarAnimation.export()
    });
  }


}

Page(pageOptions)


function getUserAvatarOriginAnimation() {
  var userAvatarAnimation = wx.createAnimation({
    duration: 1000,
    timingFunction: 'ease'
  });
  userAvatarAnimation.rotate(0).step();
  return userAvatarAnimation;
}

function getUserAvatar360Animation() {
  var userAvatarAnimation = wx.createAnimation({
    duration: 1000,
    timingFunction: 'ease'
  });
  userAvatarAnimation.rotate(360).step();
  return userAvatarAnimation;
}

function getUserAvatarParentOriginAnimation() {
  var userAvatarParentAnimation = wx.createAnimation({
    duration: 500,
    timingFunction: 'ease',
  });
  userAvatarParentAnimation.translate(-35, 0).step();
  return userAvatarParentAnimation;
}