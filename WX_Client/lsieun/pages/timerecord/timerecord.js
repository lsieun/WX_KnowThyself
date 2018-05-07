let pageOptions = {
  data: {
    thingsTypes: ["生产性工作", "会议", "技术讨论", "沟通"],
    typeIndex: 0,
    pickeStartDate: "2018-01-01",
    pickerEndDate: "2020-12-31",
    currentDate: "2018-04-01",
    pickerStartTime: "00:00",
    pickerEndTime: "23:59",
    startTime:"12:00",
    endTime: "12:30"
  },
  onLoad: function (options) {
    let now = new Date();
    //let now = getDateByStr("2018-04-21 23:59:22");
    let todayYear = now.getFullYear();
    let todayMonth = ('0' + (now.getMonth() + 1)).slice(-2);
    let todayDay = ('0' + now.getDate()).slice(-2);
    let todayHour = ('0' + now.getHours()).slice(-2);
    let todayMinutes = ('0' + now.getMinutes()).slice(-2);
    let todaySeconds = ('0' + now.getSeconds()).slice(-2);

    let todayFormat = `${todayYear}-${todayMonth}-${todayDay}`;
    let startTimeFormat = `${todayHour}:${todayMinutes}`;

    let newDate = getDateByAddMinutes(now, 30);
    let todayMaxTime = getTodayMaxTime();
    if(newDate.getTime() > todayMaxTime){
      newDate.setTime(todayMaxTime);
    }
    let newHour = ('0' + newDate.getHours()).slice(-2);
    let newMinutes = ('0' + newDate.getMinutes()).slice(-2);
    
    let endTimeFormat = `${newHour}:${newMinutes}`;
    
    this.setData({
      currentDate: todayFormat,
      startTime: startTimeFormat,
      endTime: endTimeFormat
    });
    

  },
  bindTypeChange: function (e) {
    this.setData({
      typeIndex: e.detail.value
    });
  },
  bindStartTimeChange: function (e) {
    this.setData({
      startTime: e.detail.value
    })
  },
  bindEndTimeChange: function (e) {
    this.setData({
      endTime: e.detail.value
    })
  }
}

Page(pageOptions)

function getToday() {
  let now = new Date();
  let todayYear = now.getFullYear();
  let todayMonth = ('0' + (now.getMonth() + 1)).slice(-2);
  let todayDay = ('0' + now.getDate()).slice(-2);
  let todayHour = ('0' + now.getHours()).slice(-2);
  let todayMinutes = ('0' + now.getMinutes()).slice(-2);
  let todaySeconds = ('0' + now.getSeconds()).slice(-2);

  let todayFormat = `${todayYear}-${todayMonth}-${todayDay}`;
  return todayFormat;
}

function getTodayMaxTime() {
  let today = getToday();
  console.log("today: " + today);
  let todayWithTime = today + " 23:59:59";
  console.log("todayWithTime: " + todayWithTime);
  let newDate = new Date(todayWithTime.replace("-", "/"));
  let maxTime = newDate.getTime();
  console.log("maxTime: " + maxTime);
  return maxTime;
}

function getDateByAddMinutes(date, intervalMinutes) {
  let newDate = new Date();
  newDate.setTime(date.getTime() + intervalMinutes * 60 * 1000);
  return newDate;
}

function getDateByStr(dateStr){
  let date = new Date(dateStr.replace("-", "/"));
  return date;
}


