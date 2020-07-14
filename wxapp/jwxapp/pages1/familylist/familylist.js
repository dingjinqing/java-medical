// pages1/familylist/familylist.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    if_checked: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    if(options.source){
      this.setData({
        source:options.source
      })
    }
    console.log(this.data.source)
  },
  changeDefault(){
    if(this.data.if_checked == 0){
      this.setData({
        if_checked:1
      })
    }else{
      this.setData({
        if_checked:0
      })
    }
  },
  to_pre () {
    util.jumpLink('/pages1/getprescription/getprescription')
  },
  toggleFamily(e){
    let {patientId} = e.currentTarget.dataset
    let pageList = getCurrentPages();
    let prevPage = pageList[pageList.length - 2];
    prevPage.setData({
      'params.patientId':patientId
    })
    wx.navigateBack()
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