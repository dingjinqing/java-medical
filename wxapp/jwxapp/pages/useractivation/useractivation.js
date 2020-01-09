var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var is_block = 0;
var is_fail = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    success_info: [],
    to_activation: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.data.mobile = util.getCache('mobile');
    this.request_data()
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
  request_data: function () {
    var that = this;
    util.api("/api/wxapp/user/waitactivate", function (res) {
      if (res.error == 0) {
        var success_info = res.content;
        for (var i in success_info.couponList) {
          success_info.couponList[i].startTime = success_info.couponList[i].startTime.substring(0, 10);
          success_info.couponList[i].endTime = success_info.couponList[i].endTime.substring(0, 10);
        }
        that.setData({
          success_info: success_info
        })
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, {
        user_id: util.getCache('user_id')
      })
  },

  activation: function () {
    console.log('点击')
    var that = this;
    this.data.mobile = util.getCache('mobile');
    console.log(this.data.mobile)
    if (this.data.mobile == '') {
      console.log('手机号为空')
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      console.log(is_block)
      return false; ``
    } else {
      console.log('发请求')
      util.api('/api/wxapp/user/toactivate', function (res) {
        if (res.error == 0) {
          wx.pageScrollTo({
            scrollTop: 0,
            duration: 0
          })
          that.setData({
            to_activation: 0
          })
        } if (res.error == 15231) {
          console.log('15231')
          util.removeCache("mobile")
          is_block = 1
        } else {
          // util.showModal("提示", res.content);
          // return false;
          is_fail = 1;
          that.setData({
            is_fail: is_fail,
            fail_msg: res.message
          })
        }
      }, {
          user_id: util.getCache('user_id')
        })
    }
  },

  bindGetPhoneNumberOk: function (e) {
    console.log('bindGetPhoneNumberOk')
    console.log(e)
    this.data.mobile = e.detail.phoneNumber;
  },
  close_fail: function () {
    is_fail = 0;
    this.setData({
      is_fail: is_fail
    })
  },
  to_index: function () {
    util.reLaunch({
      url: '/pages/index/index',
    })
  },
  to_integral: function () {
    util.navigateTo({
      url: '/pages/integral/integral',
    })
  },
  to_coupon: function () {
    util.navigateTo({
      url: '/pages/couponlist/couponlist',
    })
  },
})