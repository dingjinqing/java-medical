// pages1/getprescription/getprescription.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    real_name: '',
    mobile: '',
    card_id: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
  },
  realName (e) {
    this.data.real_name = e.detail.value;
  },
  mobileInput (e) {
    this.data.mobile = e.detail.value;
  },
  cardIdInput (e) {
    this.data.card_id = e.detail.value;
    prescription_info.card_id = this.data.card_id
  },
  getPrescription () {
    let prescription_info = [];
    prescription_info.real_name = this.data.real_name;
    prescription_info.mobile = this.data.mobile;
    prescription_info.card_id = this.data.card_id;
    if (!prescription_info.real_name) {
      util.showModal("提示", "请输入姓名！");
      return false;
    }
    if (!prescription_info.mobile) {
      util.showModal("提示", "请输入手机号！");
      return false;
    } else if (!/^1[3456789]\d{9}$/.test(prescription_info.mobile)) {
      util.showModal("提示", "请输入正确的手机号！");
        return false;
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