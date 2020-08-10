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
    hosCode: '',
    doctorInfo: []
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    if(this.data.bottom.user_type == 0) {
      this.data.show_modal = 1
    } else {
      this.data.show_modal = 0
    }
    this.setData({
      show_modal: this.data.show_modal
    })
    this.requestInfo();
  },
  close () {
    this.setData({
      show_modal: 0
    })
    util.jumpLink('/pages/usercenter/usercenter','reLaunch')
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
    util.api('/api/wxapp/doctor/auth', res => {
      if(res.error == 0) {
        // this.data.bottom.user_type = 1;
        // util.setCache('bottom',this.data.bottom)
        this.setData({
          show_modal: 0
        })
        this.requestInfo();
        // util.jumpLink('/pages2/doctorIndex/doctorIndex','reLaunch')
      } else if (res.error == 400031) {
        util.showModal('提示','认证失败',function(){
          util.jumpLink('/pages/usercenter/usercenter','reLaunch')
        });
      } else{
        util.showModal('提示',res.message);
        util.jumpLink('/pages/usercenter/usercenter','reLaunch')
      }
    },{
      doctorName: this.data.name,
      mobile: this.data.mobile,
      hospitalCode: this.data.hosCode,
      userId: util.getCache('user_id')
    })
  },
  requestInfo () {
    util.api('/api/wxapp/doctor/main', res => {
      if(res.error == 0) {
        this.data.doctorInfo = res.content;
        this.data.doctorInfo.departArr = [];
        for (var i in this.data.doctorInfo.departmentName){
          if(!!this.data.doctorInfo.departmentName[i]){
            this.data.doctorInfo.departArr.push(this.data.doctorInfo.departmentName[i])
          }
        }
        this.data.doctorInfo.departArr = this.data.doctorInfo.departArr.join(',');
        this.setData({
          doctorInfo: this.data.doctorInfo
        })
      }
    },{})
  },
  to_awaitPre () {
    util.jumpLink('/pages2/awaitprescribe/awaitprescribe')
  },
  viewInquiryList(e){
    let {type} = e.currentTarget.dataset
    util.jumpLink(`/pages2/inquiryList/inquiryList${util.getUrlParams({
      type
    })}`)
  },
  viewOperatePrescriptionList(){
    util.jumpLink('/pages2/operatePrescriptionList/operatePrescriptionList')
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
    this.requestInfo();
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
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    this.requestInfo();
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
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

  }
})