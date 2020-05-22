// pages1/pinintegrationdetail/pinintegrationdetail.js
var util = require('../../utils/util.js')
var app = new getApp();
var mobile = util.getCache('mobile');
global.wxPage({

  /**
   * 页面的初始数据00
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    gd: {}, // 参与活动列表
    isFold: true, // 展开列表
    title_bgColor: "#f18a4f"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    mobile = util.getCache('mobile');
    var that = this;
    // 获取当前用户参与活动
    util.api('/api/wxapp/pin/integration/myact', function (res) {
      if (res.error == 0) {
        that.setData({
          gd: res.content,
        })
      }
    });
  },

  // 查看进行中活动
  toDetail: function (e) {
    var pinInte_id = e.currentTarget.dataset.pininte_id;
    var group_id = e.currentTarget.dataset.group_id;
    util.navigateTo({
      url: '/pages1/pinintegration/pinintegration?pid=' + pinInte_id + '&gid=' + group_id,
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

  },
  toRule: function () {
    wx: util.navigateTo({
      url: '/pages/pinIntegrationRule/pinIntegrationRule',
    })
  },
  // 收起展开
  showReply: function (e) {
    var that = this;
    that.setData({
      isFold: !that.data.isFold,
    });
  },
})
