// pages1/distributionorder/distributionorder.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    userId: null,
    orderType: 0, // 订单类型
    startTime: '',
    endTime: '',
    disorderInfo: {},
    infoList: [],
    page: 1,
    lastPage: 1,
    pageRows: 20,
    isLoad: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    if (options.userId) {
      var userId = options.userId;
    } else {
      var userId = util.getCache('user_id');
    }
    if (options.username) {
      if (options.username.length > 5) {
        options.username = options.username.substring(0, 4) + '...';
      }
      this.setData({
        page_name: options.username + "的返利订单明细"
      })
    }
    var that = this;
    wx.hideShareMenu();
    if (this.data.comColor == '' || !this.data.comColor) {
      this.data.comColor = "#ff6666"
    }
    // 结束时间
    var endTime = util.formatTime(new Date());
    endTime = endTime.substring(0, 10);
    // 开始时间
    var startTime = Date.parse(endTime);
    startTime = startTime / 1000;
    startTime = startTime - 30 * 24 * 60 * 60;
    var dates = new Date(startTime * 1000);
    var Y = dates.getFullYear() + '-';
    var M = (dates.getMonth() + 1 < 10 ? '0' + (dates.getMonth() + 1) : dates.getMonth() + 1) + '-';
    var D = (dates.getDate() + 1 < 10 ? '0' + (dates.getDate() + 1) : dates.getDate() + 1);
    startTime = Y + M + D;
    that.setData({
      userId: userId,
      startTime: startTime,
      endTime: endTime
    })
    order_request(that);
  },

  // 时间选择
  bindDateChange: function (e) {
    var that = this;
    var time = e.currentTarget.dataset.time;
    var value = e.detail.value;
    if (time == 1) {
      if (value > that.data.endTime) {
        util.showModal('提示', '起始时间不能大于结束时间');
        return false;
      }
      var limitTime = Date.parse(that.data.endTime);
      limitTime = limitTime / 1000;
      limitTime = limitTime - 6 * 30 * 24 * 60 * 60;
      var dates = new Date(limitTime * 1000);
      var Y = dates.getFullYear() + '-';
      var M = (dates.getMonth() + 1 < 10 ? '0' + (dates.getMonth() + 1) : dates.getMonth() + 1) + '-';
      var D = (dates.getDate() + 1 < 10 ? '0' + (dates.getDate() + 1) : dates.getDate() + 1);
      limitTime = Y + M + D;
      if (value < limitTime) {
        util.showModal('提示', '请选择结束时间前六个月内的日期');
        return false;
      }
      that.setData({ startTime: value })
      order_request(that);
    }
    if (time == 0) {
      if (value < that.data.startTime) {
        util.showModal('提示', '结束时间不能小于开始时间');
        return false;
      }
      var limitTime = Date.parse(that.data.startTime);
      limitTime = limitTime / 1000;
      limitTime = limitTime + 6 * 30 * 24 * 60 * 60;
      var dates = new Date(limitTime * 1000);
      var Y = dates.getFullYear() + '-';
      var M = (dates.getMonth() + 1 < 10 ? '0' + (dates.getMonth() + 1) : dates.getMonth() + 1) + '-';
      var D = (dates.getDate() + 1 < 10 ? '0' + (dates.getDate() + 1) : dates.getDate() + 1);
      limitTime = Y + M + D;
      if (value > util.formatTime(new Date())) {
        util.showModal('提示', '可选日期截止为今日');
        return false;
      }
      if (value > limitTime) {
        util.showModal('提示', '请选择开始时间之后六个月内的日期');
        return false;
      }
      that.setData({ endTime: value })
      order_request(that);
    }
  },

  //切换状态
  change_order: function (e) {
    var that = this;
    var type = e.currentTarget.dataset.type;
    that.setData({
      orderType: Number(type),
      page: 1
    })
    order_request(that);
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    that.setData({ isLoad: 1 })
    if (that.data.page == that.data.lastPage) {
      that.setData({ isLoad: 0 })
      return
    }
    that.data.page = that.data.page + 1;

    util.api('/api/wxapp/distribution/rebateOrder', function (res) {
      if (res.error == 0) {
        var disorderInfo = res.content;
        var infoList = [];
        if (disorderInfo.rebateOrderInfo && disorderInfo.rebateOrderInfo.dataList && disorderInfo.rebateOrderInfo.dataList.length > 0) {
          disorderInfo.rebateOrderInfo.dataList.forEach(item => {
            item.orderSn = item.orderSn.substring(0, 5) + "********" + item.orderSn.substring(14, 19);
            item.canCalculateMoney = parseFloat(item.canCalculateMoney).toFixed(2);
            item.rebateMoney = parseFloat(item.rebateMoney).toFixed(2);
          })
          infoList = disorderInfo.rebateOrderInfo.dataList
        }
        disorderInfo.partRebateMoney = parseFloat(disorderInfo.partRebateMoney || 0).toFixed(2);
        disorderInfo.partRebateGoodsMoney = parseFloat(disorderInfo.partRebateGoodsMoney || 0).toFixed(2);
        disorderInfo.allRebateMoney = parseFloat(disorderInfo.allRebateMoney || 0).toFixed(2);
        disorderInfo.waitRebateMoney = parseFloat(disorderInfo.waitRebateMoney || 0).toFixed(2);
        if (disorderInfo.rebateOrderInfo && disorderInfo.rebateOrderInfo.page) {
          var lastPage = disorderInfo.rebateOrderInfo.page.lastPage
        }
        that.setData({
          disorderInfo: disorderInfo,
          infoList: that.data.infoList.concat(infoList),
          lastPage: lastPage,
          isLoad: 0
        })
      } else {
        util.showModal(res.message);
        return false;
      }
    }, { 
      userId: that.data.userId,
      orderType: that.data.orderType,
      startTime: that.data.startTime + ' 00:00:00',
      endTime: that.data.endTime + ' 23:59:59',
      currentPage: that.data.page,
      pageRows: that.data.pageRows
    });
  },
})
function order_request(that) {
  util.api('/api/wxapp/distribution/rebateOrder', function (res) {
    if (res.error == 0) {
      var disorderInfo = res.content;
      var infoList = [];
      if (disorderInfo.rebateOrderInfo && disorderInfo.rebateOrderInfo.dataList && disorderInfo.rebateOrderInfo.dataList.length > 0) {
        disorderInfo.rebateOrderInfo.dataList.forEach(item => {
          item.orderSn = item.orderSn.substring(0, 5) + "********" + item.orderSn.substring(14, 19);
          item.canCalculateMoney = parseFloat(item.canCalculateMoney).toFixed(2);
          item.rebateMoney = parseFloat(item.rebateMoney).toFixed(2);
        })
        infoList = disorderInfo.rebateOrderInfo.dataList
      }
      disorderInfo.partRebateMoney = parseFloat(disorderInfo.partRebateMoney || 0).toFixed(2);
      disorderInfo.partRebateGoodsMoney = parseFloat(disorderInfo.partRebateGoodsMoney || 0).toFixed(2);
      disorderInfo.allRebateMoney = parseFloat(disorderInfo.allRebateMoney || 0).toFixed(2);
      disorderInfo.waitRebateMoney = parseFloat(disorderInfo.waitRebateMoney || 0).toFixed(2);
      if (disorderInfo.rebateOrderInfo && disorderInfo.rebateOrderInfo.page) {
        var lastPage = disorderInfo.rebateOrderInfo.page.lastPage
      }
      that.setData({
        disorderInfo: disorderInfo,
        infoList: infoList,
        lastPage: lastPage
      })
    } else {
      util.showModal(res.message);
      return false;
    }
  }, { 
    userId: that.data.userId,
    orderType: that.data.orderType,
    startTime: that.data.startTime + ' 00:00:00',
    endTime: that.data.endTime + ' 23:59:59',
    currentPage: that.data.page,
    pageRows: that.data.pageRows
  });
}