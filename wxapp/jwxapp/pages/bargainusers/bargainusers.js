// pages/bargainusers/bargainusers.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var record_id;
var mobile = util.getCache('mobile');
var info_list = [];
var is_load = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page: 1,
    info_list: [],
    is_load: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    record_id = options.record_id;
    var that = this;
    list_request(that);
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
    
    util.api('/api/wxapp/bargain/user_list', function (res) {
      if (res.error == 0) {
        var info_list = [];
        if (res.content.data.length > 0) {
          info_list = res.content.data
        }
        that.setData({
          info_list: that.data.info_list.concat(res.content.data),
          is_load: 0,
          page: res.content.current_page,
          last_page: res.content.last_page
        });
      }
    }, { record_id: record_id, pageNo: that.data.page });
  },
})
function list_request(that) {
  util.api('/api/wxapp/bargain/user_list', function (res) {
    if (res.error == 0) {
      that.setData({
        info_list: res.content.data,
        page: res.content.current_page,
        last_page: res.content.last_page
      })
    }
  }, { record_id: record_id, pageNo: that.data.page });
}