// pages1/shopcheckout/shopcheckout.js
// 门店买单
var util = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    storeId: '',
    PointPaymentVisible: false, // 积分支付是否显示
    discountVisible: false, // 折扣是否显示
  },

  totalSpendingInput(e) {
    console.log(e.detail);
    var price = Number(e.detail.value);
    if (price) {
      this.setData({
        PointPaymentVisible: true,
        discountVisible: true
      });
    } else {
      this.setData({
        PointPaymentVisible: false,
        discountVisible: false
      })
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let storeId = options.storeId
    this.setData({
      storeId: storeId
    })
    let params = {
      storeId: storeId
    }
    util.api('/api/wxapp/store/info', function (res) {
      console.log(res)
    }, params)
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