// pages1/returedetail/returndetail.js
var util = require('../../utils/util.js');
var app = getApp();
var imageUrl = app.globalData.imageUrl;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    click_look: imageUrl + 'image/wxapp/click_look.png',
    returnSn: '', // 退货订单号
    orderInfo: {}, // 订单信息
    refundStatus: 0, // 订单状态
    returnType: 0, // 仅退款0 退货退款1
    totalMicroSecond: 0, // 倒计时总秒数
    clock: '', // 倒计时
    goodsImages: [], // 申请凭证图
    voucherImages: [], // 提交物流时凭证图
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let returnSn = options.return_sn
    this.setData({
      returnSn: returnSn
    })
    this.initData()
  },

  // 获取申请订单详情
  initData () {
    let that = this
    util.api('/api/wxapp/order/refund/info', function (res) {
      if (res.error === 0) {
        let orderInfo = res.content
        let refundStatus = Number(orderInfo.refundStatus)
        let returnType = Number(orderInfo.returnType)
        let totalMicroSecond = 0; // 倒计时总秒数
        // 倒计时
        if (refundStatus === 4 && returnType === 0) {
          totalMicroSecond = orderInfo.returnMoneyDays
        } else if (refundStatus === 1 && returnType === 1) {
          totalMicroSecond = orderInfo.returnAddressDays
        } else if (refundStatus === 4 && returnType === 1) {
          totalMicroSecond = orderInfo.returnShoppingDays
        } else if (refundStatus === 2) {
          totalMicroSecond = orderInfo.returnAuditPassNotShoppingDays
        }
        // 申请时凭证图
        let goodsImages = JSON.parse(orderInfo.goodsImages)
        let voucherImages = JSON.parse(orderInfo.voucherImages)
        that.setData({
          orderInfo: orderInfo,
          refundStatus: refundStatus,
          returnType: returnType,
          totalMicroSecond: totalMicroSecond,
          goodsImages: goodsImages,
          voucherImages: voucherImages
        })
        that.countdown()
      }
    }, { returnOrderSn: that.data.returnSn })
  },

  // 倒计时
  countdown: function () {
    let that = this;
    let timer;
    that.setData({
      clock: that.dateFormat(totalMicroSecond)
    })
    if (totalMicroSecond <= 0) {
      that.setData({
        clock: '已经截止'
      })
      if (timer) {
        clearTimeout(timer)
      }
      return false;
    }
    timer = setTimeout(function () {
      totalMicroSecond -= 1;
      that.countdown()
    }, 1000);
  },

  // 时间格式化输出，如3:25:19 86。每10ms都会调用一次
  dateformat: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    //天数位
    var date = Math.floor(second / 86400);
    // 小时位
    var hr = Math.floor((second - date * 24 * 3600) / 3600);
    if (hr < 10) {
      hr = "0" + hr;
    }
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60 - date * 24 * 3600) / 60);
    if (min < 10) {
      min = "0" + min;
    }
    // 秒位
    var sec = second % 60;
    if (sec < 10) {
      sec = "0" + sec;
    }
    return date + "天" + hr + '时' + min + "分" + sec + "秒";
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

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})