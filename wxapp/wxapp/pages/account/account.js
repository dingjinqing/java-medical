// pages/account/account.js
var util = require('../../utils/util.js');
var app = getApp()
var Url = app.globalData.baseUrl;
var account_list = [];
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page: 1,
    bottom: false,
    is_load:0,
    page:1,
    last_page:1,
    account_list:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    if (!util.check_setting(options)) return;
    util.api('/api/wxapp/user/number', function (res) {
      that.setData({
        num: res.content.account
      })
    }, {  })
    
    get_accout(that);
  },
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
  onReachBottom: function () {
    var that = this;
    that.setData({
      is_load: 1
    })
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return;
    }
    that.data.page = that.data.page + 1;
    util.api('/api/wxapp/account/list', function (res) {
      var account_listL = res.content;
      var ar_r = account_listL.data;
      if (ar_r.length > 0) {
        account_list = ar_r
      }
      that.setData({
        account_list: that.data.account_list.concat(account_list)
      })
    }, {   pageNo: that.data.page })
  },
})
function get_accout(that) {
  util.api('/api/wxapp/account/list', function (res) {
    var account_listL = res.content;
    that.data.last_page = account_listL.last_page;
    var ar_r = account_listL.data;
    if(ar_r.length > 0){
      account_list = ar_r
    }
    that.setData({
      account_list: account_list
    })
  }, {   pageNo: that.data.page })
}