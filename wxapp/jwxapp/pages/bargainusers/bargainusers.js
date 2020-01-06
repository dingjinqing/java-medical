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
    is_load: 0,
    timestamp: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    record_id = Number(options.record_id);
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
    
    util.api('/api/wxapp/bargain/users', function (res) {
      if (res.error == 0) {
        var info_list = [];
        if (res.content.bargainUsers.dataList.length > 0) {
          info_list = res.content.bargainUsers.dataList
        }
        that.setData({
          info_list: that.data.info_list.concat(res.content.bargainUsers.dataList),
          is_load: 0,
          page: res.content.bargainUsers.page.currentPage,
          last_page: res.content.bargainUsers.page.lastPage
        });
      }
    }, { record_id: record_id, pageNo: that.data.page });
  },
})
function list_request(that) {
  util.api('/api/wxapp/bargain/users', function (res) {
    if (res.error == 0) {
      that.setData({
        info_list: res.content.bargainUsers.dataList,
        page: res.content.bargainUsers.page.currentPage,
        last_page: res.content.bargainUsers.page.lastPage,
        timestamp: res.content.timestamp
      })

      // 砍价列表时间
      if (that.data.info_list.length > 0) {
        var now = new Date(that.data.timestamp).getTime();
        that.data.info_list.forEach((item, index) => {
          item.allTime = (now - new Date(item.createTime).getTime()) / 1000;
          if (item.allTime < 60) {
            item.show_time = '刚刚'
          } else if (item.allTime < 3600) {
            item.show_time = Math.ceil(item.allTime / 60) + '分钟前'
          } else if (item.allTime < 24 * 3600) {
            item.show_time = Math.ceil(item.allTime / 3600) + '小时前'
          } else {
            item.show_time = Math.ceil(item.allTime / (24 * 3600)) + '天前'
          }
        })
        that.setData({
          info_list: that.data.info_list,
        })
      }

    }
  }, { recordId: record_id, pageNo: that.data.page });
}