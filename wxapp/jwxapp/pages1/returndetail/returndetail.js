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
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let orderSn = options.order_sn
    this.initData()
  },

  // 获取申请订单详情
  initData () {
    util.api('/api/wxapp/order/refund/info', function(res) {
      if (res.error === 0) {
        console.log(res.content)

      }
    }, {})
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