// pages/storelist/storelist.js
var util = require('../../utils/util');
var app = getApp();

Page({
  /**
   * 页面的初始数据
   */
  data: {
    goods_id: '',
    list: [],
    de_type: 2,
    scan_stores: "",
    imageUrl: app.globalData.imageUrl,
    pageParams: {}
  },

  listRequest: function (that) {
    util.getUserLocation(function () {
      util.api('/api/wxapp/store/list', function (res) {
        that.setData({
          list: res.content
        })
      }, {
        scan_stores: that.data.scan_stores,
        location: JSON.stringify(location),
        type: that.data.de_type,
        goods_id: that.data.goods_id,
        currentPage: 1
      })
    })
  },
  jumpUrl: function (e) {
    let id = e.currentTarget.dataset.id;
    let state = e.currentTarget.dataset.state;
  },

  onIndex: function (e) {
    util.navigateTo({ url: '/pages/index/index' })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    let de_type = options.de_type;
    let scan_stores = options.scan_stores;
    let goods_id = options.goods_id;
    this.setData({
      scan_stores: scan_stores ? scan_stores : 0,
      de_type: de_type,
      goods_id: goods_id
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    let that = this;
    that.listRequest(that);
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading();
    var that = this;
    that.listRequest(that);
    wx.hideNavigationBarLoading();
    wx.stopPullDownRefresh();
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
    return {
      path: 'pages/storelist/storelist?invite_id=' + util.getCache('user_id')
    }
  }
})