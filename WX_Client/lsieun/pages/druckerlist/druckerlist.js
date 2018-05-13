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
    plain: false,
    firstResult: 0,
    maxResult: 3,
    hasMore: true
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //requestData(that, mCurrentPage + 1);
    refreshDruckerList(this, this.data.firstResult, this.data.maxResult);
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
  /**
 * 页面相关事件处理函数--监听用户下拉动作
 */
  onPullDownRefresh: function () {
    console.log("下拉刷新")
    let that = this;
    that.setData({
      itmes: [],
      firstResult: 0,
      maxResult: 3,
      hasMore: true
    })
    refreshDruckerList(this, this.data.firstResult, this.data.maxResult);
  },
  onReachBottom: function () {
    console.log('onReachBottom')
    var that = this
    that.setData({
      hidden: false,
    });
    //requestData(that, mCurrentPage + 1);
    if (!that.data.hasMore) return;
    refreshDruckerList(this, this.data.firstResult, this.data.maxResult);
  }
};

Page(handler);

function refreshDruckerList(page, firstResult, maxResult) {
  var that = page;

  var url = app.globalData.host + app.urls.drucker_list;
  var order = "desc";

  wx.request({
    url: url,
    data: {
      "firstResult": firstResult,
      "maxResult": maxResult,
      "order": order
    },
    method: "POST",
    success: function (res) {
      var flag = res.data.success;
      console.log("drucker list==>" + JSON.stringify(res.data));

      if (flag != true) {
        var toastText = "获取数据失败" + res.data.msg;
        wx.showToast({
          title: toastText,
          icon: 'none',
          duration: 2000
        });
        return;
      }

      var druckerList = res.data.dataList;
      var hasMore = true;
      var num = 0;
      if (druckerList == null || druckerList.length < 1) {
        hasMore = false;
      }
      num = druckerList.length;

      var newDruckerList = [];
      var index = 0;
      for (var i = 0; i < page.data.items.length; i++) {
        var drucker = page.data.items[i];
        newDruckerList[index] = drucker;
        index++;
      }
      for (var i = 0; i < druckerList.length; i++) {
        var drucker = druckerList[i];
        newDruckerList[index] = drucker;
        index++;
      }

      page.setData({
        items: newDruckerList,
        firstResult: firstResult + num,
        hasMore: hasMore
      });
    }
  })
}