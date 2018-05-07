//taskcreate.js

let handler = {
  data: {
    taskid: undefined,
    taskname:"",
    prioritys: ["重要且紧急", "重要不紧急", "紧急不重要", "不重要也不紧急"],
    priorityIndex: 1,
    pickeStartDate: "2018-01-01",
    pickerEndDate: "2038-01-31",
    startDate: "2018-04-01",
    endDate: "2018-04-01",
    addUrl: "http://127.0.0.1:8888/knowthyself/task/add",
    modifyUrl: "http://127.0.0.1:8888/knowthyself/task/modify"

  },
  onLoad: function (options) {
    var that = this;

    let today = new Date(),
      todayYear = today.getFullYear(),
      todayMonth = ('0' + (today.getMonth() + 1)).slice(-2),
      todayDay = ('0' + today.getDate()).slice(-2);
    let todayFormat = `${todayYear}-${todayMonth}-${todayDay}`;
    this.setData({
      startDate: todayFormat,
      endDate: todayFormat
    });

    if (options.taskid == undefined){
      return;
    }

    wx.request({
      url: 'http://127.0.0.1:8888/knowthyself/task/info',
      data: { "uid": options.taskid},
      method: "POST",
      success: function(res){
        var flag = res.data.success;
        console.log("res.data==>" + JSON.stringify(res.data));
        if (flag == true){
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
    console.log("this.data==> " + JSON.stringify(this.data));
    console.log("e.detail==> " + JSON.stringify(e.detail));

    var that = this;
    var userid = "441517664024657920";
    var taskname = e.detail.value.name;
    var taskpriority = this.data.priorityIndex;
    var startTime = this.data.startDate;
    var endTime = this.data.endDate;

    var mydata = {
      "uid": this.data.taskid,
      "userid": userid,
      "name": taskname,
      "priority": taskpriority,
      "startTime": startTime,
      "endTime": endTime
    };
    console.log(JSON.stringify(mydata));

    if(!taskname){
      wx.showToast({
        title: '任务名称不能为空',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    var url = this.data.addUrl;
    if (that.data.taskid) {
      url = this.data.modifyUrl;
    }

    wx.request({
      url: url,
      data: mydata,
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {
        'content-type': 'application/json'
      },
      success: function(res){
        console.log("formSubmit==>res==>" + JSON.stringify(res));
        var flag = res.data.success;
        var toastText = "操作成功!";
        if(flag != true) {
          toastText = "操作失败 " + res.data.msg;
        }
        wx.showToast({
          title: toastText,
          icon: 'none',
          duration: 2000
        });
        if(that.data.taskid == undefined) {
          console.log("添加任务成功");
          wx.navigateBack({
            delta: 1
          })
        }

      }
    })
    
  }, 
  formReset: function(e){
    //
  }
}

Page(handler)

//TODO
// 任务名称不能为空
// 开始时间不能大于结束时间


