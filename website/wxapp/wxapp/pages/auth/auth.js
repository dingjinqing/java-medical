// pages/auth/auth.js
const util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = '';
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      mobile = util.getCache('mobile') ? util.getCache('mobile') : '';
      this.setData({
        mobile: mobile,
        page_success:true
      })
  },
  btn_index: function () {
    util.redirectTo({
      url: '/pages/index/index'
    })
  },
  // 绑定手机号
  auth_mobile: function (e) {
    var that = this;
    if (e.detail.errMsg == 'getPhoneNumber:ok') {
      var iv = e.detail.iv;
      var encryptedData = e.detail.encryptedData;
      util.checkSession(function(){
        that.parseMobile(iv, encryptedData);
      });
    }
  },
  // 解析手机号
  parseMobile: function (iv, data) {
    var that = this;
    util.api('/api/wxapp/wxdecrypt', function (res) {
      if (res.error == 0) {
        util.setCache("mobile", res.content.phoneNumber);
        mobile = res.content.phoneNumber;
        that.btn_index();
      } else if (res.error == 41001) {
        util.wxLogin(function () {

        })
      } else {
        util.showModal('提示', '授权失败，请重试！');
      }
    }, {   iv: iv, crypt_data: data })
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


})
