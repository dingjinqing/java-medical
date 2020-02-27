// pages1/pinintegrationdetail/pinintegrationdetail.js
var util = require('../../utils/util.js')
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var good_id = '';
var mobile = util.getCache('mobile');
var total_micro_second = 0;
var pinInte_id;
var group_id;
var gd;

global.wxPage({

  /**
   * 页面的初始数据00
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    total_micro_second: 0,
    isFold: true,
    title_bgColor: "#f18a4f"
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    mobile = util.getCache('mobile');
    var that = this;
    // util.api('/api/wxapp/pin/integration/myact', function (res) {
    //   gd = res.content;
    //   if (res.content) {
    //     util.api('/api/wxapp/user_goods/record', function (res1) {

    //     }, { goods_id: group_id, active_id: options.pinInte_id, active_type: 7, type: 1 })
    //   }
    //   that.setData({
    //     gd: gd,
    //   })
    // }, { pinInte_id: options.pinInte_id, group_id: group_id });
  },

  // 查看进行中活动
  toDetail: function (e) {
    var pinInte_id = e.currentTarget.dataset.pininte_id;
    var groupp_id = e.currentTarget.dataset.group_id;
    util.navigateTo({
      url: '/pages/pinintegration/pinintegration?pinInte_id=' + pinInte_id + '&group_id=' + groupp_id,
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
