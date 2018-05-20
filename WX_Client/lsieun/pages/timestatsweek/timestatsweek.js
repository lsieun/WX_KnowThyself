'use strict';

// 引入工具类库 
import util from '../../utils/index.js';
import * as echarts from '../../ec-canvas/echarts';

let app = getApp();

let handler = {
  data: {
    ec: {
      lazyLoad: true
    }
  },

  onReady() {

    if (!app.globalData.userInfo || !app.globalData.userInfo.uid) {
      wx.showToast({
        title: '您好像没有登录哎~~~',
        icon: 'none',
        duration: 3000
      });
      return;
    }

    var that = this;
    // 获取组件
    this.ecComponent = this.selectComponent('#mychart-timeline-pie');

    var userid = app.globalData.userInfo.uid;
    //var currentDay = util.getToday();
    var now = new Date();
    var newDate = util.getDateByAddMinutes(now, (0 - 60 * 9));
    var currentDay = util.getyyMMdd(newDate);

    wx.request({
      url: app.globalData.host + app.urls.timestat_week,
      data: {
        "userid": userid,
        "currentDay": currentDay
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
        var chart_data_list = res.data.dataList;
        if (!chart_data_list || chart_data_list.length < 1) return;
        var title = res.data.data.title;
        initEChart(that, title, chart_data_list);
      }
    });
  }
};

function initEChart(page, title, chart_data_list) {
  var that = page;

  that.ecComponent.init((canvas, width, height) => {
    // 获取组件的 canvas、width、height 后的回调函数
    // 在这里初始化图表
    const chart = echarts.init(canvas, null, {
      width: width,
      height: height
    });
    //setOption(chart);
    setOptionAndData(chart, title, chart_data_list);

    // 将图表实例绑定到 this 上，可以在其他成员函数（如 dispose）中访问
    that.chart = chart;

    // 注意这里一定要返回 chart 实例，否则会影响事件处理等
    return chart;
  });
}

Page(handler);

function setOptionAndData(chart, title, chart_data_list) {

  var colorArray = [];
  for (var i = 0; i < chart_data_list.length; i++) {
    colorArray[i] = getColor();
  }

  var option = {
    backgroundColor: "#ffffff",
    // color: ["#2F9323", "#D9B63A", "#2E2AA4", "#9F2E61", "#4D670C", "#BF675F", "#1F814A", "#357F88", "#673509", "#310937", "#1B9637", "#F7393C"],
    color: colorArray,
    title: {
      text: title,
      x: 'center',
      y: 10,
      textStyle: {
        color: '#333333',
        fontSize: 20,
        fontWeight: 'normal',
      },
    },	    
    series: [{
      label: {
        normal: {
          fontSize: 12
        }
      },
      type: 'pie',
      center: ['50%', '50%'],
      radius: [0, '50%'],
      data: chart_data_list,
      itemStyle: {
        emphasis: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 2, 2, 0.3)'
        },
        normal: {
          label: {
            show: true,
            formatter: '{b} : {c}\n({d}%)'
          },
          labelLine: { show: true }
        }
      }
    }],
    // toolbox: {
    //   show: true,
    //   // orient: 'vertical',
    //   // left: 'right',
    //   // top: 'center',
    //   feature: {
    //     dataView: { readOnly: false },
    //     restore: {},
    //     saveAsImage: {}
    //   }
    // },
  };

  chart.setOption(option);
}

//定义一个函数，实现随机生成十六进制颜色值
function getColor() {
  //定义字符串变量colorValue存放可以构成十六进制颜色值的值
  var colorValue = "0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f";
  //以","为分隔符，将colorValue字符串分割为字符数组["0","1",...,"f"]
  var colorArray = colorValue.split(",");
  var color = "#";//定义一个存放十六进制颜色值的字符串变量，先将#存放进去
  //使用for循环语句生成剩余的六位十六进制值
  for (var i = 0; i < 6; i++) {
    //colorArray[Math.floor(Math.random()*16)]随机取出
    // 由16个元素组成的colorArray的某一个值，然后将其加在color中，
    //字符串相加后，得出的仍是字符串
    color += colorArray[Math.floor(Math.random() * 16)];
  }
  return color;
} 