// pages/splitinfo/splitinfo.js
var app = new getApp();
var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    coupon_id: '',
    shop_id: '',
    coupon_sn: '',
    user: '',
    split_info: [],
    split_info1: [],
    get_count: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    that.setData({
      user: options.user,
      coupon_id: options.coupon_id,
      shop_id: options.shop_id,
      coupon_sn: options.coupon_sn
    })
    // user = options.user;
    // coupon_id = options.coupon_id;
    // shop_id = options.shop_id;
    // coupon_sn = options.coupon_sn;
    
    reLoad(that);
  },
  to_store: function () {
    util.redirectTo({
      url: '/pages/index/index',
    })
  },
  get_this_coupon: function (e) {
    var that = this;
    that.data.get_count++;
    if (that.data.get_count > 1) return;
    // util.api('/api/wxapp/coupon/getsplit', function (res) {
    //   that.setData({
    //     split_info1: res.content
    //   })
    //   if (that.data.split_info1.error == -4) {
    //     util.showModal('提示', '优惠券领取成功!');
    //     reLoad(that);
    //     get_count = 0;
    //   }
    // }, { 
    //   user: that.data.user, 
    //   coupon_id: that.data.coupon_id, 
    //   shop_id: that.data.shop_id, 
    //   coupon_sn: that.data.coupon_sn
    // })
  },
  goIndex: function () {
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
      path: '/pages/splitinfo/splitinfo?user=' + this.data.user + "&coupon_sn=" + this.data.coupon_sn + "&coupon_id=" + this.data.coupon_id + "&invite_id=" + util.getCache('user_id'),
      imageUrl: this.data.imageUrl + 'image/wxapp/share_icon.jpg',

    }
  }
})
function reLoad(that) {
  // util.api('/api/wxapp/coupon/splitinfo', function (res) {
  //   var split_info = res.content;
  //   var user_list = split_info.user_list;
  //   that.setData({
  //     split_info: split_info
  //   })
  // }, { coupon_id: that.data.coupon_id, shop_id: that.data.shop_id, user: that.data.user, coupon_sn: that.data.coupon_sn })
}
