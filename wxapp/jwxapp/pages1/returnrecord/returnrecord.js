// pages1/returnrecord/returnrecord.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var order_sn;
var ret_id;
var record_info = [];

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    baseUrl: app.globalData.baseUrl,
    order_sn: '',
    ret_id: '',
    record_info: [],
    can_shipping_fee: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    order_sn = options.order_sn;
    wx.hideShareMenu();
    ret_id = options.ret_id;
    util.api('/api/wxapp/return/change', function (res) {
      if (res.error == 0) {
        record_info = res.content;
        var can_shipping_fee = '0.00';
        if (record_info.order.order_status == 3) {
          can_shipping_fee = record_info.return_info.can_return_free != undefined ? record_info.return_info.can_return_free : 0;
        }
        record_info.order.type_icon = ''
        record_info.order.goods_type = record_info.order.goods_type.split(",");
        for (var j in record_info.order.goods_type) {
          if (record_info.order.goods_type[j] == 1) {
            record_info.order.type_icon = "拼团"
          } else if (record_info.order.goods_type[j] == 3) {
            record_info.order.type_icon = '砍价'
          } else if (record_info.order.goods_type[j] == 5) {
            record_info.order.type_icon = "秒杀"
          } else {
            record_info.order.type_icon == ""
          }
        }
        that.setData({
          record_info: record_info,
          can_shipping_fee: can_shipping_fee,
        })
      }
    }, { order_sn: order_sn, ret_id: ret_id })
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