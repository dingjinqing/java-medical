// pages1/medicalrecordinfo/medicalrecordinfo.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    id:1,
    medicalInfo: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.data.id = options.id || 1;
    this.requestInfo();
  },
  requestInfo () {
    util.api('/api/wxapp/medicine/history/detail', res=> {
      if (res.error == 0) {
        if(res.content.sex == 0) {
          res.content.sex_text = '未知'
        } else if (res.content.sex == 1) {
          res.content.sex_text = '男'
        } else {
          res.content.sex_text = '女'
        }
        res.content.visitTime = res.content.visitTime.substr(0, 10);
        this.data.medicalInfo = res.content;
        this.setData({
          medicalInfo: this.data.medicalInfo
        })
      } else {
        util.showModal('提示', res.message)
        return false
      }
    },{id:this.data.id})
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