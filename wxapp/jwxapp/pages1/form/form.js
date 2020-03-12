// pages1/form/form.js
var util = require("../../utils/util.js")
var decorate = require("../../pages/common/decorate.js")

global.wxPage({
  mixins: [decorate],

  /**
   * 页面的初始数据
   */
  data: {
    _options: {},
    pageContent: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    this.setData({
      _options:options
    })
    this.requestFormPageData(options.page_id, this.requestCallback.bind(this))
  },

  requestCallback(pageContent) {
    this.setData({
      pageContent: pageContent
    })
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
    wx.stopPullDownRefresh();
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
    util.api("/api/wxapp/share/record", function(res) {}, {
      activity_id: this._options.page_id,
      activity_type: 6
    });
    return {
      path: "pages1/form/form?page_id=" + this._options.page_id + "&invite_id=" + util.getCache('user_id'),
    }
  }
})