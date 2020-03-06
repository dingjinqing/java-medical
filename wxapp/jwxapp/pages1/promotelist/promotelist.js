// pages1/promotelist/promotelist.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var promote_list = [];
var is_load = 0;
var launch_id;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page: 1,
    last_page: 1,
    promote_list: [],
    is_load: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    launch_id = options.launch_id;
    promote_request(that);
  },
  /**
   * 页面上拉触底事件的处理函数
   */
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
    util.api('/api/wxapp/promote/detailList', function (res) {
      var servL = res.content;
      that.data.last_page = servL.last_page;
      var server_list_r = res.content.data;
      var promote_list = [];
      if (server_list_r.length > 0) {
        promote_list = server_list_r;
      }
      that.setData({
        promote_list: that.data.promote_list.concat(promote_list),
        is_load: 0
      });
    }, { pageNo: that.data.page, launch_id: launch_id });
  },

})
function promote_request(that) {
  // util.api('/api/wxapp/promote/detailList', function (res) {
  //   var servL = res.content;
  //   that.data.last_page = servL.last_page;
  //   var server_list_r = res.content.data;
  //   var promote_list = [];
  //   if (server_list_r.length > 0) {
  //     promote_list = server_list_r;
  //   }
  //   that.setData({
  //     promote_list: promote_list,
  //   });
  // }, { pageNo: that.data.page, launch_id: launch_id });
}