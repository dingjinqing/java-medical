// pages/widthdrawrecord/widthdrawrecord.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var account_info = [];
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_load: 0,
    page: 1,
    last_page: 1,
    account_info: [],
    total_money: '0.00'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    util.api('/api/wxapp/distributor/withdraw/list', function (res) {
      if (res.error == 0) {
        var servL = res.content.data;
        if (res.content.done) {
          that.data.total_money = res.content.done;
          that.data.total_money = parseFloat(that.data.total_money).toFixed(2);
        }
        that.data.last_page = servL.page;
        var server_list_r = servL.dataList;
        var account_info = [];
        if (server_list_r.length > 0) {
          account_info = server_list_r;

        }
        that.setData({
          account_info: account_info,
          total_money: that.data.total_money
        })
      }
    }, { pageNo: that.data.page })
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
    var that = this;
    that.setData({
      is_load: 1
    })
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return;
    }
    that.data.page = that.data.page + 1;
    util.api('/api/wxapp/distributor/withdraw/list', function (res) {
      if (res.error == 0) {
        var servL = res.content.data;
        if (res.content.done) {
          that.data.total_money = res.content.done;
          that.data.total_money = parseFloat(that.data.total_money).toFixed(2);
        }
        var server_list_r = servL.data;
        var account_info = [];
        if (server_list_r.length > 0) {
          account_info = server_list_r;
        }
        that.setData({
          account_info: that.data.account_info.concat(account_info),
          total_money: that.data.total_money
        })
      }
    }, { pageNo: that.data.page })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})