//taskcreate.js
'use strict';

// 引入工具类库 
import util from '../../utils/index.js';

//获取应用实例
var app = getApp();

let handler = {
  data: {
    userid: "",
    taskid: undefined,
    taskname: "",
    prioritys: ["重要且紧急", "重要不紧急", "紧急不重要", "不重要也不紧急"],
    priorityIndex: 1,
    pickeStartDate: "2018-01-01",
    pickerEndDate: "2038-01-31",
    startDate: "2018-04-01",
    endDate: "2018-04-01"

  },
  onLoad: function (options) {
    var that = this;

    var today = util.getToday();
    this.setData({
      startDate: today,
      endDate: today
    });

    if (options.taskid == undefined) {
      return;
    }

    var taskid = options.taskid;
    displayTaskInfo(that, taskid);

  },
  bindPriorityChange: function (e) {
    this.setData({
      priorityIndex: e.detail.value
    });
  },
  bindStartDateChange: function (e) {
    this.setData({
      startDate: e.detail.value
    });
  },
  bindEndDateChange: function (e) {
    this.setData({
      endDate: e.detail.value
    });
  },
  formSubmit: function (e) {
    var that = this;

    if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
      wx.showToast({
        title: '您好像没有登录哎~~~',
        icon: 'none',
        duration: 3000
      });
      return;
    }

    var userid = app.globalData.userInfo.uid;
    var taskname = e.detail.value.name;
    var taskpriority = this.data.priorityIndex;
    var startTime = this.data.startDate;
    var endTime = this.data.endDate;

    var startDate = util.getDateByStr(startTime + " 00:00:01");
    var endDate = util.getDateByStr(endTime + " 23:59:59");

    var mydata = {
      "uid": this.data.taskid,
      "userid": userid,
      "name": taskname,
      "priority": taskpriority,
      "startTime": startTime,
      "endTime": endTime
    };

    if (!taskname) {
      wx.showToast({
        title: '任务名称不能为空',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    if (endDate < startDate){
      wx.showToast({
        title: '"结束日期"不能小于"开始日期"',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    var url = app.globalData.host + app.urls.task_add;
    if (that.data.taskid) {
      url = app.globalData.host + app.urls.task_modify;
    }

    wx.request({
      url: url,
      data: mydata,
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {

        var flag = res.data.success;
        var toastText = "操作成功!";
        if (flag != true) {
          toastText = "操作失败 " + res.data.msg;
        }

        wx.showToast({
          title: toastText,
          icon: 'none',
          duration: 2000,
          success: function(){
            if (that.data.taskid == undefined && flag) {
              console.log("添加任务成功");
              wx.navigateBack({
                delta: 1
              })
            }
          }
        });



      }
    })

  },
  formReset: function (e) {
    var today = util.getToday();
    this.setData({
      startDate: today,
      endDate: today
    });
  }
}

Page(handler)

//TODO
// 任务名称不能为空
// 开始时间不能大于结束时间

function displayTaskInfo(page, taskid) {
  var that = page;
  wx.request({
    url: app.globalData.host + app.urls.task_info,
    data: { "uid": taskid },
    method: "POST",
    success: function (res) {
      var flag = res.data.success;

      if (flag == true) {
        var task = res.data.data;
        var taskid = task.uid;
        var taskname = task.name;
        var priority = task.priority;
        var startTime = task.startTime;
        var endTime = task.endTime;

        that.setData({
          taskid: taskid,
          taskname: taskname,
          priorityIndex: priority,
          startDate: startTime,
          endDate: endTime
        });
      }
    }
  });
}
