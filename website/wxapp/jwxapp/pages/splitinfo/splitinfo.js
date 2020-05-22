// pages/splitinfo/splitinfo.js
var app = new getApp();
var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    couponSn: '',
    couponId: '',
    shareUserId: '', // 分享者id
    split_info: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    console.log(options)
    var that = this;
    that.setData({
      couponSn: options.couponSn,
      couponId: options.couponId,
      shareUserId: options.inviteId
    })
    reLoad(that);
  },

  // 立即领取
  get_this_coupon: function (e) {
    var that = this;
    util.api('/api/wxapp/coupon/split/get', function (res) {
      if (res.error == 0) {
        if (res.content.status == 1) {
          util.showModal('提示', '优惠券领取成功!');
          reLoad(that);
        }
      } else {
        util.showModal('提示', res.message);
        return false;
      }
    }, { 
      couponId: that.data.couponId,
      couponSn: that.data.couponSn,
      shareUserId: that.data.shareUserId
    })
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
 * 用户点击右上角分享,立即分享
 */
  onShareAppMessage: function (res) {
    var that = this;
    return {
      title: '分享优惠券',
      path: '/pages/splitinfo/splitinfo?couponSn=' + that.data.couponSn + "&couponId=" + that.data.couponId + "&inviteId=" + util.getCache('user_id'),
      imageUrl: that.data.imageUrl + 'image/wxapp/share_icon.jpg'
    }
  }
})
function reLoad(that) {
  util.api('/api/wxapp/coupon/split/detail', function (res) {
    if (res.error == 0) {
      that.setData({
        split_info: res.content
      })
    } else {
      util.showModal('提示', res.message);
      return false;
    }
  }, { 
    couponId: that.data.couponId, 
    couponSn: that.data.couponSn,
    shareUserId: that.data.shareUserId, 
  })
}
