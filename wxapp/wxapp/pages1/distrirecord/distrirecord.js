// pages/distrirecord/distrirecord.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var grade_info = [];
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
    util.api('/api/wxapp/distributor/level/record', function (res) {
        if(res.error == ""){
          grade_info = res.content;
          that.setData({
            grade_info: grade_info
          })
        }
    }, { })
  },
})