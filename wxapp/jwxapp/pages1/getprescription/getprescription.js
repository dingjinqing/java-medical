// pages1/getprescription/getprescription.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    real_name: '',
    mobile: '',
    card_id: '',
    if_show_agree: 0,
    if_agree: 0
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
    this.data.card_id = e.detail.value
  },
  close_modal (e) {
    this.setData({
      if_show_agree: 0
    })
  },
  not_agree () {
    wx.navigateBack({})
  },
  agree () {
    this.setData({
      if_show_agree: 0,
      if_agree: 1
    })
  },
  getPrescription () {
    let prescription_info = [];
    prescription_info.name = this.data.real_name;
    prescription_info.mobile = this.data.mobile;
    prescription_info.identityCode = this.data.card_id;
    if (this.data.if_show_agree == 0 && this.data.if_agree == 0) {
      this.setData({
        if_show_agree:1
      });
      return false;
    }
    if (!prescription_info.name) {
      util.showModal("提示", "请输入姓名！");
      return false;
    }
    if (!prescription_info.identityCode) {
      util.showModal("提示", "请输入身份证号！");
      return false;
    }
    if (!prescription_info.mobile) {
      util.showModal("提示", "请输入手机号！");
      return false;
    } 
    if (!/^1[3456789]\d{9}$/.test(prescription_info.mobile)) {
      util.showModal("提示", "请输入正确的手机号！");
        return false;
    } 
    util.api('/api/wxapp/user/patient/get/info', res => {
      if(res.error != 0){
        
      } else {
        util.showModal('提示', '您暂无本医院就诊记录，请先添加就诊人', () => {
          util.jumpLink('/pages1/patientinfo/patientinfo?prescription_info=' + prescription_info);
        }, true, '取消', '去添加')
      }
    },{
      userId: util.getCache("user_id"),
      name: prescription_info.name,
      mobile: prescription_info.mobile,
      identityCode: prescription_info.identityCode,
    })
  },
  addFamily () {
    let prescription_info = [];
    prescription_info.name = this.data.real_name;
    prescription_info.mobile = this.data.mobile;
    prescription_info.identityCode = this.data.card_id;
    if (this.data.if_show_agree == 0 && this.data.if_agree == 0) {
      this.setData({
        if_show_agree:1
      });
      return false;
    }
    if (!prescription_info.name) {
      util.showModal("提示", "请输入姓名！");
      return false;
    }
    if (!prescription_info.identityCode) {
      util.showModal("提示", "请输入身份证号！");
      return false;
    }
    if (!prescription_info.mobile) {
      util.showModal("提示", "请输入手机号！");
      return false;
    } 
    if (!/^1[3456789]\d{9}$/.test(prescription_info.mobile)) {
      util.showModal("提示", "请输入正确的手机号！");
        return false;
    } 
    util.jumpLink('/pages1/patientinfo/patientinfo?prescription_info=' + prescription_info);
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