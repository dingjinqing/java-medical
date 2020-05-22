// pages/splitcoupon/splitcoupon.js
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var user;
var coupon;
var shop_id;
var split_info= [];
var split_info1 = [];
var order_sn;
var cou_limit;
var form_id;
var open_id;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    split_info:[],
    split_info1:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    user = options.user;
    coupon = options.coupon;
    shop_id = options.shop_id;
    order_sn = options.order_sn;
    cou_limit = options.cou_limit;
    var that = this;
    util.api('/api/wxapp/coupon/getsplitinfo',function(res){
      split_info = res.content;
      var coupon_info = split_info.coupon;
      if (coupon_info.validity == null){
        split_info.start_time = coupon_info.start_time.substr(0, 10);
        split_info.end_time = coupon_info.end_time.substr(0, 10);
      }
      that.setData({
        split_info: split_info
      })
    }, {   user: user, coupon: coupon, shop_id: shop_id, order_sn: order_sn, cou_limit: cou_limit});
  },
  get_this_coupon:function(e){
    form_id = e.detail.formId;
    open_id = wx.getStorageSync("openid");
    var that = this;
    util.api('/api/wxapp/coupon/getsplit', function (res) {
      split_info1 = res.content;
      if (split_info1.error == -4){
        util.navigateTo({
          url: '/pages/splitinfo/splitinfo?coupon_id=' + coupon + "&shop_id=" + shop_id + "&user=" + user + "&order_sn=" + order_sn + "&cou_limit=" + cou_limit,
        })
      }
      if (split_info1.error == 0){
        util.navigateTo({
          url: '/pages/splitinfo/splitinfo?coupon_id=' + coupon + "&shop_id=" + shop_id + "&user=" + user + "&order_sn=" + order_sn + "&cou_limit=" + cou_limit,
        })
      }
    }, {   user: user, coupon: coupon, shop_id: shop_id, order_sn: order_sn, cou_limit: cou_limit,form_id:form_id,open_id:open_id})
  },
  goIndex:function(){
    util.redirectTo({
      url: '/pages/index/index',
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

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
