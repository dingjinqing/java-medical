// pages/balance/balance.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var is_block;
var mobile = util.getCache('mobile');
var account_info = [];
var is_bind_mobile;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    baseUrl: app.globalData.baseUrl,
    is_block: 0,
    account_info: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    util.api('/api/wxapp/user/account/withdraw', function (res) {
      if (res.error == 0) {
        account_info = res.content;
        is_bind_mobile = account_info.is_bind_mobile;
        is_block = 0;
        that.setData({
          account_info: account_info,
          is_block: is_block
        })
      }
    }, {})
  },
  give_tips: function () {
    util.showModal("提示", "账户余额中已返利佣金可提现");
  },
  // 余额明细
  yue_record: function () {
    util.navigateTo({
      url: '/pages/account/account',
    })
  },
  // 提现记录
  withdraw_record: function () {
    util.navigateTo({
      url: '/pages/widthdrawrecord/widthdrawrecord',
    })
  },
  // 提现
  to_money: function () {
    var that = this;
    if (account_info.withdrawStatus == 0 || account_info.withdrawStatus == null) {
      util.showModal("提示", "系统暂时不支持提现");
      return false
    }
    if (account_info.withdraw == 0) {
      util.showModal("提示", "暂无可提现余额");
      return false
    }
    if (is_bind_mobile == 1 && util.getCache('mobile') == '') {
      util.checkSession(function () {
        that.setData({
          is_block: is_block = 1
        })
      })
      return false;
    }
    util.navigateTo({
      url: '/pages/widthdraw/widthdraw',
    })
  },
})