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
