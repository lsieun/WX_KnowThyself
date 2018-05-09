// pages/taskall/taskall.js
'use strict';

// 引入工具类库 
import util from '../../utils/index.js';

//获取应用实例
var app = getApp();

let handler = {
  data: {
    taskList: [],
    taskPrioritys: ["/images/warn_64_red.png", "/images/warn_64_blue.png", "/images/warn_64_pink.png", "/images/warn_64_lightblue.png"],
    firstResult: 0,
    maxResult: 10,
    hasMore: true
  },
  onLoad: function (options) {
    refreshTaskList(this, this.data.firstResult, this.data.maxResult);
  },
  onShow: function () {
    // 页面显示
    
  },
  onReachBottom() {
    console.log("Hello World");
    refreshTaskList(this, this.data.firstResult, this.data.maxResult);
  },
  taskStatusChange: function (e){
    var taskid = e.target.dataset.taskid;
    var flag = e.detail.value;
    changeTaskStatus(taskid, !flag);
  },
  editTask: function (e) {
    console.log("editTask===>" + JSON.stringify(e));
    var taskid = e.currentTarget.dataset.taskid;
    wx.navigateTo({
      url: '../taskcreate/taskcreate?taskid=' + taskid,
    })
  },
  delTask: function (e){
    var that = this;
    console.log("e==>" + JSON.stringify(e));
    var taskid = e.currentTarget.dataset.taskid;
    var taskname = e.currentTarget.dataset.taskname;
    var shortname = "";
    if (taskname.length > 3) {
      shortname = taskname.substring(0, 3) + "...";
    }
    else {
      shortname = taskname;
    }
    wx.showModal({
      title: "删除任务",
      content: '您确定要删除"' + shortname + '"任务吗？"',
      confirmText: "确定",
      cancelText: "取消",
      success: function (res) {
        console.log(res, '>>');
        if (res.confirm == true) {
          wx.request({
            url: app.globalData.host + app.urls.task_del,
            data: {
              uid: taskid
            },
            method: "POST",
            success: function (res) {
              var flag = res.data.success;
              console.log("delTask==>" + JSON.stringify(res.data));
              if (flag != true) {
                var toastText = "删除任务失败" + res.data.msg;
                wx.showToast({
                  title: toastText,
                  icon: 'none',
                  duration: 2000
                });
                return;
              }
            },
            complete: function () {
              that.setData({
                taskList: []
              });
              refreshTaskList(that, "0", that.data.firstResult);
            }
          })
        }
      }
    })
  }
};

Page(handler)

function refreshTaskList(page, firstResult, maxResult) {
  if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
    wx.showToast({
      title: '您好像没有登录哎~~~',
      icon: 'none',
      duration: 3000
    });
    return;
  }
  var url = app.globalData.host + app.urls.task_all;
  var userid = app.globalData.userInfo.uid;
  var order = "asc";

  wx.request({
    url: url,
    data: {
      "userid": userid,
      "firstResult": firstResult,
      "maxResult": maxResult,
      "order": "desc"
    },
    method: "POST",
    success: function (res) {
      var flag = res.data.success;
      console.log("taskall==>" + JSON.stringify(res.data));

      if (flag != true) {
        var toastText = "获取数据失败" + res.data.msg;
        wx.showToast({
          title: toastText,
          icon: 'none',
          duration: 2000
        });
        return;
      }

      var taskList = res.data.dataList;
      var hasMore = true;
      var tasknum = 0;
      if(taskList == null || taskList.lenght < 1){
        hasMore = false;
      }
      tasknum = taskList.length;

      var newTaskList = [];
      var taskIndex = 0;
      for (var i = 0; i < page.data.taskList.length; i++){
        var task = page.data.taskList[i];
        newTaskList[taskIndex] = task;
        taskIndex++;
      }
      for(var i=0; i < taskList.length; i++){
        var task = taskList[i];
        newTaskList[taskIndex] = task;
        taskIndex++;
      }

      page.setData({
        taskList: newTaskList,
        firstResult: firstResult + tasknum,
        hasMore: hasMore
      });
    }
  })
}

function changeTaskStatus(taskid, taskDone){
  var url = '';
  if (taskDone == false) {
    url = 'http://127.0.0.1:8888/knowthyself/task/undo';
  }
  else {
    url = 'http://127.0.0.1:8888/knowthyself/task/done';
  }

  wx.request({
    url: url,
    data: {
      uid: taskid
    },
    method: "POST",
    success: function (res) {
      var flag = res.data.success;
      console.log("taskStatusChange==>" + JSON.stringify(res.data));
      if (flag != true) {
        var toastText = "同步数据失败" + res.data.msg;
        wx.showToast({
          title: toastText,
          icon: 'none',
          duration: 2000
        });
        return;
      }
    },
    complete: function () {
    }
  })

}


