// pages1/promotelist/promotelist.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    page: 1, // 当前页
    last_page: 1,
    promote_list: [], // 列表数据
    is_load: 0,
    launch_id: '', // 发起助力id
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    that.setData({
      launch_id: options.launch_id
    })
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
    // util.api('/api/wxapp/promote/detailList', function (res) {
    //   that.setData({
    //     promote_list: that.data.promote_list.concat(res.content),
    //     last_page: res.content.page.last_page,
    //     is_load: 0
    //   });
    // }, { pageNo: that.data.page, launch_id: that.data.launch_id });
  },

})
function promote_request(that) {
  util.api('/api/wxapp/promote/detailList', function (res) {
    if (res.error == 0) {
      that.setData({
        promote_list: res.content
        // last_page: res.content.page.last_page
      });
    }
  }, { pageNo: that.data.page, launchId: that.data.launch_id });
}