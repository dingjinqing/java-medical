// pages/shoporderinfo/shoporderinfo.js
var app = getApp();
var Url = app.globalData.baseUrl;
var util = require('../../utils/util.js');
var order_sn;
var info = [];
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    order_sn = options.order_sn;
    var that = this;
    info = [];
    var windowHeight;
    wx.getSystemInfo({
      success: function (res) {
        windowHeight = res.windowHeight;
        that.setData({
          windowHeight: windowHeight
        })
      }
    })
    util.api('/api/wxapp/store/orderinfo', function (res) {
      console.log(res)
      if(res.error == 0){
        info = res.content;
        that.setData({
          info: info
        })
      }
    }, { order_sn: order_sn})
  },
  showMap: function (e) {
    var latitude = Number(info.store_info.latitude);
    var longitude = Number(info.store_info.longitude);
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
      wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },
})
