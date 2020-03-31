// pages1/distrirecord/distrirecord.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    grade_info: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    // util.api('/api/wxapp/distributor/level/record', function (res) {
    //   if (res.error == "") {
    //     that.setData({
    //       grade_info:  res.content
    //     })
    //   }
    // }, {})
  },
  /**
    * 用户点击右上角分享
    */
  onShareAppMessage: function () {
    return {
      path: this.route + '?invite_id=' + util.getCache('user_id')
    }
  },
})