// pages1/familylist/familylist.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    if_checked: 0,
    patientList: []
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
    this.requestList();
  },
  changeDefault(e){
    util.api('/api/wxapp/user/patient/set/default', res => {
      if(res.error == 0){
        this.requestList();
      }else{
        util.showModal('提示',res.message)
        return false
      }
    },{
      userId: util.getCache('user_id'),
      patientId: e.currentTarget.dataset.id
    })
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
  requestList () {
    util.api('/api/wxapp/user/patient/list', res => {
      if (res.error == 0) {
        this.data.patientList = res.content
        this.setData({
          patientList: this.data.patientList
        })
      }else{
        util.showModal('提示',res.message)
        return false
      }
    },{
      userId: util.getCache('user_id')
    })
  },
  edit_patient (e) {
    util.jumpLink('/pages1/patientinfo/patientinfo?is_edit=1&patient_id=' + e.currentTarget.dataset.id)
  },
  del_patient (e) {
    util.showModal('提示', '确定要删除该患者吗？', () => {
      util.api('/api/wxapp/user/patient/delete', res => {
        if(res.error == 0){
          util.toast_success('删除成功!')
          this.requestList()
        } else {
          util.showModal('提示', res.message)
          return false
        }
      },{
        userId: util.getCache('user_id'),
        patientId: e.currentTarget.dataset.id
      })
    }, true, '取消', '去添加')
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
    this.requestList();
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