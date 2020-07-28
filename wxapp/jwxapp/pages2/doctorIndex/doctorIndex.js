// pages2/doctorIndex/doctorIndex.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    show_modal: 0,
    name: '',
    mobile: '',
    hosCode: ''
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
  },
  close () {
    this.setData({
      show_modal: 0
    })
  },
  docName (e) {
    this.data.name = e.detail.value
  },
  docMobile (e) {
    this.data.mobile = e.detail.value
  },
  docCode (e) {
    this.data.hosCode = e.detail.value
  },
  to_verify () {
    if(!this.data.name){
      util.showModal('提示','请输入医生姓名');
      return false
    }
    if(!this.data.mobile){
      util.showModal('提示','请输入医生手机号');
      return false
    }
    if(!this.data.hosCode){
      util.showModal('提示','请输入医生院内编号');
      return false
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