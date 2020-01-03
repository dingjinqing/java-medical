// pages1/shoporderinfo/shoporderinfo.js
var app = getApp();
var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    order_sn: '',
    orderInfo: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      orderSn: options.order_sn
    })
    this.initData()
  },

  initData () {
    let that = this
    util.api('/api/wxapp/store/pay/orderDetail', function (res) {
      if (res.error === 0) {
        let orderInfo = res.content
        that.setData({
          orderInfo: orderInfo
        })
      }
    }, { orderSn: that.data.orderSn })
  },

  // 查看地图
  showMap: function (e) {
    var latitude = Number(orderInfo.store_info.latitude);
    var longitude = Number(orderInfo.store_info.longitude);
    wx.openLocation({
      latitude: latitude,
      longitude: longitude,
      scale: 28
    })
  },

  to_index: function () {
    util.navigateTo({
      url: '/pages/index/index'
    })
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