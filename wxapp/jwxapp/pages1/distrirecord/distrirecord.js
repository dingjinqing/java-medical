// pages1/distrirecord/distrirecord.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    gradeInfo: [],
    currentPage: 1,
    lastPage: 1,
    rowPages: 20
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this
    util.api('/api/wxapp/distributor/level/record', res => {
      if (res.error == 0) {
        that.setData({
          gradeInfo:  res.content.dataList,
          currentPage: res.content.page.currentPage,
          lastPage: res.content.page.lastPage
        })
        console.log(that.data.gradeInfo)
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, {
      userId: util.getCache('user_id'),
      currentPage: that.data.currentPage,
      rowPages: that.data.rowPages
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    wx.showLoading({
      title: '加载中',
    })
    if (that.data.currentPage == that.data.lastPage) {
      wx.hideLoading();
      return;
    }
    that.data.currentPage = that.data.currentPage + 1;
    util.api('/api/wxapp/distributor/level/record', res => {
      wx.hideLoading();
      if (res.error == 0) {
        that.setData({
          gradeInfo:  that.data.gradeInfo.concat(res.content.dataList),
          currentPage: res.content.page.currentPage,
          lastPage: res.content.page.lastPage
        })
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, {
      userId: util.getCache('user_id'),
      currentPage: that.data.currentPage,
      rowPages: that.data.rowPages
    })
  },

  /**
    * 用户点击右上角分享
    */
  onShareAppMessage: function () {
    return {
      path: this.route + '?inviteId=' + util.getCache('user_id')
    }
  },
})