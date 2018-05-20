'use strict';

import config from './config'

let util = {
  isDEV: config.isDev,
  log() {
    this.isDEV && console.log(...arguments)
  },
  alert(title = '提示', content = config.defaultAlertMsg) {
    if ('object' === typeof content) {
      content = this.isDEV && JSON.stringify(content) || config.defaultAlertMsg
    }
    wx.showModal({
      title: title,
      content: content
    })
  },
  formatTime(date){
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()
    const hour = date.getHours()
    const minute = date.getMinutes()
    const second = date.getSeconds()

    return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':')
  },
  formatNumber(n) {
    n = n.toString()
    return n[1] ? n : '0' + n
  },
  getDateByStr(dateStr) {
    //IOS只识别2017/01/01这样的日期格式
    let date = new Date(dateStr.replace(/-/g, '/'));
    return date;
  },
  getDateByAddMinutes(date, intervalMinutes) {
    let newDate = new Date();
    newDate.setTime(date.getTime() + intervalMinutes * 60 * 1000);
    return newDate;
  },
  getyyMMdd(date){
    let year = date.getFullYear();
    let month = ('0' + (date.getMonth() + 1)).slice(-2);
    let day = ('0' + date.getDate()).slice(-2);
    let hour = ('0' + date.getHours()).slice(-2);
    let minutes = ('0' + date.getMinutes()).slice(-2);
    let seconds = ('0' + date.getSeconds()).slice(-2);

    let dateFormat = `${year}-${month}-${day}`;
    return dateFormat;
  },
  gethhmm(date){
    let hour = ('0' + date.getHours()).slice(-2);
    let minutes = ('0' + date.getMinutes()).slice(-2);
    let hhmm = `${hour}:${minutes}`;
    return hhmm;
  },
  getToday() {
    let now = new Date();
    let todayYear = now.getFullYear();
    let todayMonth = ('0' + (now.getMonth() + 1)).slice(-2);
    let todayDay = ('0' + now.getDate()).slice(-2);
    let todayHour = ('0' + now.getHours()).slice(-2);
    let todayMinutes = ('0' + now.getMinutes()).slice(-2);
    let todaySeconds = ('0' + now.getSeconds()).slice(-2);

    let todayFormat = `${todayYear}-${todayMonth}-${todayDay}`;
    return todayFormat;
  },
  getTodayMaxTime(date) {
    let yyyyMMdd = this.getyyMMdd(date);
    let todayWithTime = yyyyMMdd + " 23:59:59";

    let newDate = new Date(todayWithTime.replace("-", "/"));
    let maxTime = newDate.getTime();

    return maxTime;
  },
  getStorageData(key, cb) {
    let self = this;

    // 将数据存储在本地缓存中指定的 key 中，会覆盖掉原来该 key 对应的内容，这是一个异步接口
    wx.getStorage({
      key: key,
      success(res) {
        cb && cb(res.data);
      },
      fail(err) {
        let msg = err.errMsg || '';
        if (/getStorage:fail/.test(msg)) {
          self.setStorageData(key)
        }
      }
    })
  },
  setStorageData(key, value = '', cb) {
    wx.setStorage({
      key: key,
      data: value,
      success() {
        cb && cb();
      }
    })
  } 
};

export default util
