// pages1/patientinfo/patientinfo.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    prescription_info: [],
    patient_info: {},
    showModal: 0,
    // 性别
    sex_index: -1,
    gender: ['男', '女'],
    pat_name: '',
    pat_age: '',
    pat_mobile: '',
    pat_id_num: '',
    pat_tip: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    if(options.prescription_info) {
      this.data.prescription_info  = options.prescription_info
    }
  },
  nameInput (e) {
    this.data.pat_name = e.detail.value
  },
  ageInput (e) {
    this.data.pat_age = e.detail.value
  },
  bindSexChange (e) {
    this.data.sex_index = e.detail.value;
    this.setData({
      sex_index: this.data.sex_index
    })
  },
  idInput (e) {
    this.data.pat_id_num = e.detail.value
  },
  mobileInput (e) {
    this.data.pat_mobile = e.detail.value
  },
  tipInpuit (e) {
    this.data.pat_tip = e.detail.value
  },
  close_modal () {
    this.setData({
      showModal: 0
    })
  },
  open_modal () {
    this.setData({
      showModal: 1
    })
  },
  bindSubmit () {
    if ( !this.data.pat_name) {
      util.showModal("提示", "请输入真实姓名");
      return false;
    }
    if ( !this.data.pat_age) {
      util.showModal("提示", "请输入年龄");
      return false;
    }
    if (this.data.sex_index == -1) {
      util.showModal("提示", "请选择性别");
      return false;
    }
    var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if (this.data.pat_id_num.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请输入正确的身份证号");
      return false;
    }
    if (!(re.test(this.data.pat_id_num)) || this.data.pat_id_num.replace(/^\s+|\s+$/g, '').length != 18) {
      util.showModal("提示", "请输入正确的身份证号");
      return false;
    }
    if (!this.data.pat_mobile) {
      util.showModal("提示", "请输入手机号");
      return false;
    } 
    if (!/^1[3456789]\d{9}$/.test(this.data.pat_mobile)) {
      util.showModal("提示", "请输入正确的手机号！");
        return false;
    } 
    if(!this.data.pat_tip) {
      util.showModal("提示", "请输入备注");
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