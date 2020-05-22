// pages/payment/payment.js
var app = getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
var reco_goods = [];
var order_info = [];
var order_sn;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    reco_goods: [],
    show_split: 0,
    has_reco_goods: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    order_sn = options.order_sn;
    var coupon = options.coupon;
    // this.recommend_goods();
    this.request_that(order_sn, coupon);
  },
  request_that: function (order_sn, coupon) {
    var _this = this;
    util.api('/api/wxapp/order/info', function (res) {
      if(res.error == 0) {
        order_info = res.content;

        if (coupon == 1 && order_info.coupon != "" && order_info.type == 1) {
          order_info.show_split = 1;
        }
        if (order_info.error == 0 && order_info.type == 3){
          order_info.is_display = 1;
        }
        if (order_info.coupon.cou_limit > order_info.coupon.surplus) {
          order_info.cou_xianshi = order_info.coupon.surplus;
        } else {
          order_info.cou_xianshi = order_info.coupon.cou_limit;
        }
        if (order_info.coupon != '' && order_info.coupon.type == 4){
          let custom_info = {
            link: order_info.coupon.pay_reward_url,
            img_src: order_info.coupon.pay_reward_img_path
          }
          _this.setData({
            custom_show:true,
            custom_info: custom_info
          })
        }
        order_info.new_score_integral = parseInt(order_info.score_discount * 100);
        _this.setData({
          order_info: order_info,
        })
      }
    }, { order_sn: order_sn, coupon: coupon, cou_limit: order_info.cou_xianshi })
  },
  to_order:function (e) {
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id })
    if (order_info.type == 3 && order_info.error == 0){
      util.navigateTo({
        url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn
      })
    } else {
      util.navigateTo({
        url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn + "&coupon=1"
      })
    }
  },
  to_index: function (e) {
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id })
    util.redirectTo({
      url: '/pages/index/index'
    })
  },

  //分享优惠券弹框
  guan: function () {
    var that = this;
    order_info.show_split = 0;
    that.setData({
      order_info: order_info
    })
  },
  kai: function () {
    var that = this;
    order_info.show_split = 0;
    that.setData({
      order_info: order_info
    })
  },
  close_cou:function(){
    order_info.is_display = 0;
    this.setData({
      order_info:order_info
    })
  },
  go_couponlist:function(){
    order_info.is_display = 0;
    this.setData({
      order_info: order_info
    });
    util.navigateTo({
      url: '/pages/couponlist/couponlist',
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
  onShareAppMessage: function (res) {
    return {
      title: '分享优惠券',
      path: '/pages/splitcoupon/splitcoupon?user=' + order_info.user_id + "&coupon=" + order_info.coupon.id + "&shop_id=" + order_info.shop_id + "&order_sn=" + order_info.order_sn + "&cou_limit=" + order_info.cou_xianshi + "&invite_id=" + util.getCache('user_id'),
      imageUrl: imageUrl + 'image/wxapp/share_icon.jpg',
    }
  }
})
