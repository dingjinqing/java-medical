// pages2/prescribeinfo/prescribeinfo.js
var util = require('../../utils/util.js');
const zh_CN = require('../../utils/i18n/zh_CN.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    orderId: '',
    page_info: [],
    doc_name: '',
    // 诊断内容
    diagnosis: '',
    // 科室相关
    depart_arr: [],
    depart_name: '',
    depart_id: '',
    depart_all: [],
    depart_index: -1,
    // 医嘱
    doctorAdvice: '',
    // 开方请求的参数
    doctorId: '',
    goodsIdList: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    if(options.orderId) {
      this.data.orderId = options.orderId
    }
    console.log(this.data.page_info);
    this.requestPatient();
    this.requestInfo();
  },
  requestPatient () {
    util.api('/api/wxapp/order/medical/get', res => {
      if(res.error == 0){
        this.data.page_info = res.content[0];
        let advice_arr = [];
        this.data.goodsIdList = [];
        for(let i in this.data.page_info.goodsMedicalOneInfoVoList){
          advice_arr.push(this.data.page_info.goodsMedicalOneInfoVoList[i].goodsCommonName)
          this.data.goodsIdList.push(this.data.page_info.goodsMedicalOneInfoVoList[i].goodsId)
        }
        this.data.page_info.advice = advice_arr.join(',');
        this.data.page_info.now_time = util.formatTime(new Date()).substr(0, 10);
        this.setData({
          page_info: this.data.page_info
        })
      }
    },{orderId:this.data.orderId})
  },
  requestInfo () {
    util.api('/api/wxapp/doctor/auth/info', res => {
      if(res.error == 0){
        this.data.doc_name = res.content.name;
        this.data.doctorId = res.content.id;
        this.setData({
          doc_name: this.data.doc_name
        })
      }
    },{})
    util.api('/api/wxapp/department/list', res => {
      if(res.error == 0){
        this.data.depart_all = res.content
        for (let i in res.content){
          this.data.depart_arr.push(res.content[i].name)
        }
        this.setData({
          depart_arr: this.data.depart_arr
        })
      }
    },{keyword:''})
  },
  bindClear () {
    this.setData({diagnosis: ''})
  },
  disInput (e) {
    this.data.diagnosis = e.detail.value;
  },
  adviInput (e) {
    this.data.doctorAdvice = e.detail.value;
  },
  changeDepart (e) {
    this.data.depart_index = e.detail.value;
    this.data.depart_id = this.data.depart_all[e.detail.value].id;
    this.data.depart_name = this.data.depart_all[e.detail.value].name
    this.setData({
      depart_index: this.data.depart_index
    })
  },
  btn_check () {
    this.data.goodsIdList = this.data.goodsIdList.join(',')
    util.api('/api/wxapp/order/prescription/make', res => {
      if(res.error == 0){
        util.toast_success('成功')
        util.jumpLink('/pages2/awaitprescribe/awaitprescribe')
      }
    },{
      orderId: this.data.orderId,
      auditStatus: 1,
      patientId: this.data.page_info.patient.id,
      doctorId: this.data.doctorId,
      departmentCode: this.data.depart_id,
      departmentName: this.data.depart_name,
      diagnosisName: this.data.diagnosis,
      doctorAdvice: this.data.doctorAdvice,
      goodsIdList: this.data.goodsIdList
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