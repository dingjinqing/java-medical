// pages/splitinfo/splitinfo.js
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
var coupon_id;
var shop_id;
var user;
var order_sn;
var getsplit_info=[];
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    getsplit_info:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    user = options.user;
    coupon_id = options.coupon_id;
    shop_id = options.shop_id;
    order_sn = options.order_sn;
    var that = this;
    util.api('/api/wxapp/coupon/splitinfo', function (res) {
        getsplit_info = res.content;
        var user_list = getsplit_info.user_list;

        that.setData({
          getsplit_info: getsplit_info
        })
    }, { coupon: coupon_id, shop_id: shop_id, user: user, order_sn: order_sn })
  },
  to_store:function(){
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
 * 用户点击右上角分享,立即分享
 */
  onShareAppMessage: function (res) {

    if (res.from === 'button') {
    }
    return {
      title: '分享优惠券',
      path: '/pages/splitcoupon/splitcoupon?user=' + user + "&coupon=" + coupon_id + "&shop_id=" + shop_id + "&order_sn=" + order_sn + "&invite_id=" + util.getCache('user_id') ,
      imageUrl:imageUrl + 'image/wxapp/share_icon.jpg',

    }
  }
})
