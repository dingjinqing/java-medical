// pages/usercenter/usercenter.js
var util = require('../../utils/util.js');
var app = getApp()
var Url = app.globalData.baseUrl;
var imageUrl = app.globalData.imageUrl;
var is_sign = 0;
var is_grade = 0;
var user_center;
var page_style = 1;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    user_name: app.globalData.input_array.mobile,
    wx_user_avatar: '',
    user_mobile: '',
    user_avatar: '',
    is_sign: 0,
    is_grade: 0,
    main_url: app.globalData.baseUrl,
    imageUrl: app.globalData.imageUrl,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    is_block: 0,
    page_style: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    var win_h = wx.getSystemInfoSync().windowHeight;
    this.setData({
      win_h: win_h
    })
    that.usercenterRequest(that);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  usercenterRequest: function (that) {
    util.api('/api/wxapp/account/usercenter', function (res) {
      if (res.error == 0) {
        console.log("个人中心的",res)
        var user_center_all = res.content.module_data;
        page_style = user_center_all[0].page_style;
        if (user_center_all[0].module_name == "global") {
          user_center = user_center_all.slice(1);
        } else {
          user_center = user_center_all
        }

        console.log(user_center);

        for (var i in user_center) {
          if (user_center[i].module_name == "center_header") {
            if (user_center[i].qrcode && user_center[i].qrcode.status == 1) {
              var myQrCode = user_center[i].qrcode.qrcode.qrcode_img
              wx.setStorage({
                key: 'myQrCode',
                data: myQrCode,
              })
              that.setData({
                myQrCode: myQrCode
              })
            }
          }
          if (user_center[i].module_name == "appointment") {
            if (user_center[i].appointment_info && user_center[i].appointment_info.service_img) {
              user_center[i].appointment_info.service_img = JSON.parse(user_center[i].appointment_info.service_img)[0];
            }
          }
        }
        that.setData({
          user_center: user_center,
          page_style: page_style
        })
      }
    }, {})
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var user_name = util.getCache('nickName');
    var wx_user_avatar = util.getCache('avatarUrl');
    var that = this;
    if (user_name != '' && wx_user_avatar != '') {
      this.setData({
        wx_user_avatar: wx_user_avatar,
        user_name: user_name
      })
    }
  },
  to_account: function (e) {
    var num = e.currentTarget.dataset.num;
    if (num == null) {
      num = '0.00';
    }
    util.navigateTo({
      url: '/pages/balance/balance'
    })
  },
  to_integral: function (e) {
    var num = e.currentTarget.dataset.num;
    if (num == null) {
      num = 0;
    }
    util.navigateTo({
      url: '/pages/integral/integral?num=' + num
    })
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

  }
})