// pages1/messagelist/messagelist.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_system: 1,
    is_order: 0,
    is_advisory: 0,
    system_notice: "消费累计1234.56元，可持就诊人医保卡至本院划卡至本院划卡至本院划卡。",
    if_show_all:0,
    system_notice1: '' 
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.data.system_notice1 = this.data.system_notice.substr(0,25) + "..."
    this.setData({
      system_notice1: this.data.system_notice1
    })
  },
  change_square (e) {
    var nav_index = e.currentTarget.dataset.nav_index;
    if (nav_index == 0) {
      this.setData({
        is_system: 1,
        is_order: 0,
        is_advisory: 0
      })
    } else if(nav_index == 1){
      this.setData({
        is_system: 0,
        is_order: 1,
        is_advisory: 0
      })
    } else{
      this.setData({
        is_system: 0,
        is_order: 0,
        is_advisory: 1
      })
    }
  },
  bindChangeStyle (e) {
    if (e.currentTarget.dataset.change == 0) {
      this.setData({
        if_show_all:1
      })
    } else {
      this.setData({
        if_show_all:0
      })
    }
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

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})