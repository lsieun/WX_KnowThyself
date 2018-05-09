'use strict';

// 引入工具类库 
import util from '../../utils/index.js';

//获取应用实例
var app = getApp();

let handler = {
  data:{
    userid:"",
    taskList:null,
    taskToDoList:null,
    taskDoneList:null,
    taskPrioritys: ["/images/warn_64_red.png", "/images/warn_64_blue.png", "/images/warn_64_pink.png", "/images/warn_64_lightblue.png"]
  },
  onLoad: function (options){
    var that = this;
    displayTaskList(that);
  },
  onShow: function () {
    // 页面显示
    // var that = this;
    // displayTaskList(that);
  },

  taskStatusChange: function(e){
    var that = this;

    var taskid = e.target.dataset.taskid;

    var flag = e.detail.value;
    var taskDone = true;
    if(flag == true){
      taskDone = false;
    }

    changeTaskStatus(that, taskid, taskDone);
  },
  delTask: function (e){
    var that = this;

    var taskid = e.currentTarget.dataset.taskid;
    var taskname = e.currentTarget.dataset.taskname;
    var shortname = "";
    if (taskname.length > 3){
      shortname = taskname.substring(0,3) + "...";
    }
    else{
      shortname = taskname;
    }

    wx.showModal({
      title: "删除任务",
      content: '您确定要删除"' + shortname + '"任务吗？"',
      confirmText: "确定",
      cancelText: "取消",
      success: function (res) {
        if(res.confirm == true){
          delServerTask(that, taskid);
        }
      }
    })
  },
  editTask: function(e){
    var taskid = e.currentTarget.dataset.taskid;
    wx.navigateTo({
      url: '../taskcreate/taskcreate?taskid=' + taskid,
    })
  }

};

Page(handler);

function delServerTask(page, taskid){
  var that = page;
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
      displayTaskList(page);
    }
  })
}

function changeTaskStatus(page, taskid, taskDone){
  var that = page;

  var url = '';
  if (taskDone == true) {
    url = app.globalData.host + app.urls.task_done;
  }
  else {
    url = app.globalData.host + app.urls.task_undo;
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
      displayTaskList(page);
    }
  })
}

function displayTaskList(page){
  if (app.globalData.userInfo && app.globalData.userInfo.uid) {
    var userid = app.globalData.userInfo.uid;
    page.setData({
      userid: userid
    });
    var currentDay = util.getToday();
    refreshTaskList(page,userid,currentDay);
  }
  else {
    setTimeout(function () {
      displayTaskList(page);
    }, 2000);
  }
}

function refreshTaskList(page, userid, currentDay) {
  var that = page;

  wx.request({
    url: app.globalData.host + app.urls.task_daylist,
    data: {
      "userid": userid,
      "currentDay": currentDay,
      "firstResult": "0",
      "maxResult": "20",
      "order": "asc"
    },
    method: "POST",
    success: function (res) {
      let flag = res.data.success;
      console.log("task==>" + JSON.stringify(res.data));
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
      showTaskListInPage(page, taskList);
    }
  });
}

function showTaskListInPage(page, taskList){
  var that = page;
  if (!taskList) {
    that.setData({
      taskList: null,
      taskToDoList: null,
      taskDoneList: null
    });
  }
  else {
    var taskTODOList = new Array();
    var taskDONEList = new Array();
    var indexTODO = 0;
    var indexDONE = 0;
    for (var i = 0; i < taskList.length; i++) {
      var task = taskList[i];
      if (task.status == 1) {
        taskDONEList[indexDONE] = task;
        indexDONE++;
      }
      else {
        taskTODOList[indexTODO] = task;
        indexTODO++;
      }
    }

    that.setData({
      taskList: taskList,
      taskToDoList: taskTODOList,
      taskDoneList: taskDONEList
    });
  }
}
