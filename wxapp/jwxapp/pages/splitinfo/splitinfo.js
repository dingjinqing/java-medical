// pages/splitinfo/splitinfo.js
var app = new getApp();
var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    user: '',
    couponSn: '',
    couponId: '',

    split_info: [],
    split_info1: [],
    get_count: 0 // 领取人数
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    that.setData({
      user: options.user,
      couponSn: options.couponSn,
      couponId: options.couponId
    })
    reLoad(that);
  },

  // 立即领取
  get_this_coupon: function (e) {
    var that = this;
    that.data.get_count++;
    if (that.data.get_count > 1) return;
    // util.api('/api/wxapp/coupon/getsplit', function (res) {
    //   if (res.error == 0) {
    //     that.setData({
    //       split_info1: res.content
    //     })
    //     if (that.data.split_info1.error == -4) {
    //       util.showModal('提示', '优惠券领取成功!');
    //       reLoad(that);
    //       get_count = 0;
    //     }
    //   }
    // }, { 
    //   user: that.data.user, 
    //   couponId: that.data.couponId,
    //   couponSn: that.data.couponSn
    // })
  },

  // 随便逛逛
  goIndex: function () {
    util.redirectTo({
      url: '/pages/index/index',
    })
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
    return {
      title: '分享优惠券',
      path: '/pages/splitinfo/splitinfo?user=' + this.data.user + "&couponSn=" + this.data.couponSn + "&couponId=" + this.data.couponId + "&inviteId=" + util.getCache('user_id'),
      imageUrl: this.data.imageUrl + 'image/wxapp/share_icon.jpg'
    }
  }
})

function reLoad(that) {
  // util.api('/api/wxapp/coupon/splitinfo', function (res) {
  //   if (res.error == 0) {
  //     that.setData({
  //       split_info: res.content
  //     })
  //   } else {
  //     util.showModal('提示', res.message);
  //     return false;
  //   }
  // }, { 
  //   user: that.data.user, 
  //   couponId: that.data.couponId, 
  //   couponSn: that.data.couponSn 
  // })
}
