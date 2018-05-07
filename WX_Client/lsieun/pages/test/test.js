//test.js  

// 引入工具类库 
import util from '../../utils/index';

//获取应用实例  
var app = getApp()
Page({
  data: {
    imgUrls: [
      {
        link: '/pages/index/index',
        url: 'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg'
      }, {
        link: '/pages/logs/logs',
        url: 'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg'
      }, {
        link: '/pages/test/test',
        url: 'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg'
      }
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    userInfo: {}
  },
  onLoad: function () {
    console.log('onLoad test');
  },
  test_wx_login: function() {
    wx.login({
      success: function(res){
        if(res.code){
          console.log("res.code = " + res.code);
          console.log("res.errMsg = " + res.errMsg);
        }
      }
    });
  },
  test_wx_getuserinfo: function(){
    wx.getUserInfo({
      // withCredentials: true,
      success: function(res){
        var userInfo = res.userInfo
        var nickName = userInfo.nickName
        var avatarUrl = userInfo.avatarUrl
        var gender = userInfo.gender //性别 0：未知、1：男、2：女
        var province = userInfo.province
        var city = userInfo.city
        var country = userInfo.country
        console.log("res = " + JSON.stringify(res));
        console.log("nickName = " + nickName);
        console.log("avatarUrl = " + avatarUrl);
        console.log("gender = " + gender);
        console.log("province = " + province);
        console.log("city = " + city);
        console.log("country = " + country);
      }
    })
  },
  test_wx_request: function(){
    wx.request({
      url: 'http://127.0.0.1:8888/knowthyself/user/info?uid=439430904427839488',
      success: function(res){
        console.log(JSON.stringify(res));
      }
    })
  },
  test_kt_signin: function(){
    
  },
  test_kt_util: function () {
    console.log("AAAAA");
    util.log("Hello WOrldddddd");
  },
})  