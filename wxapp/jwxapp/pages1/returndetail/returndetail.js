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
        that.orderInfo = res.content
      }
    }, { returnOrderSn: that.data.returnSn })
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