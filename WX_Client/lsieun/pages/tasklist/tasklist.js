let handler = {
  data:{
    taskList:null,
    taskToDoList:null,
    taskDoneList:null,
    taskPrioritys: ["/images/warn_64_red.png", "/images/warn_64_blue.png", "/images/warn_64_pink.png", "/images/warn_64_lightblue.png"],
  },
  onLoad: function (options){
    
  },
  onShow: function () {
    // 页面显示
    this.refreshTaskList("441517664024657920", "2018-05-04");
  },
  refreshTaskList: function(userid, currentDay){
    var that = this;

    wx.request({
      url: 'http://127.0.0.1:8888/knowthyself/task/daylist',
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
        if (taskList == null) {
          that.setData({
            taskList: null,
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
            taskToDoList: taskTODOList,
            taskDoneList: taskDONEList
          });
        }
      }
    });
  },
  taskStatusChange: function(e){
    var that = this;
    console.log('switch1 发生 change 事件，携带值为', e.detail.value)
    console.log('switch1 发生 change 事件，携带值为', JSON.stringify(e))
    var taskid = e.target.dataset.taskid;
    var url = '';
    var flag = e.detail.value;
    if(flag == true){
      url = 'http://127.0.0.1:8888/knowthyself/task/undo';
    }
    else{
      url = 'http://127.0.0.1:8888/knowthyself/task/done';
    }
    wx.request({
      url: url,
      data: {
        uid: taskid
      },
      method: "POST",
      success: function (res){
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
      complete: function(){
        that.refreshTaskList("441517664024657920", "2018-05-04");
      }
    })
  },
  delTask: function (e){
    var that = this;
    console.log("e==>" + JSON.stringify(e));
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
        console.log(res, '>>');
        if(res.confirm == true){
          wx.request({
            url: 'http://127.0.0.1:8888/knowthyself/task/del',
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
              that.refreshTaskList("441517664024657920", "2018-05-04");
            }
          })
        }
      }
    })
  },
  editTask: function(e){
    console.log("editTask===>" + JSON.stringify(e));
    var taskid = e.currentTarget.dataset.taskid;
    wx.navigateTo({
      url: '../taskcreate/taskcreate?taskid=' + taskid,
    })
  }

};

Page(handler)