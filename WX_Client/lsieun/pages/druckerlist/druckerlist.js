//timeline.js
'use strict';

// 引入工具类库 
import util from '../../utils/index.js';

//获取应用实例
var app = getApp();

let handler = {
  data: {
    items: [],
    hidden: false,
    loading: false,
    plain: false
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //requestData(that, mCurrentPage + 1);
  },
  onItemClick: function (event) {
    var targetUrl = "/pages/druckerimage/druckerimage";
    if (event.currentTarget.dataset.url != null) {
      targetUrl = targetUrl + "?url=" + event.currentTarget.dataset.url;
    }
    wx.navigateTo({
      url: targetUrl
    });
  },
  onReachBottom: function () {
    console.log('onLoad')
    var that = this
    that.setData({
      hidden: false,
    });
    //requestData(that, mCurrentPage + 1);
  }  
};

Page(handler);