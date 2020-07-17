// pages1/prescriptioninfo/prescriptioninfo.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    prescriptionInfo: [],
    prescriptionNo: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.data.prescriptionCode = options.prescriptionCode || 1;
    this.requestInfo();
  },
  requestInfo () {
    util.api('/api/wxapp/prescription/details', res => {
      if (res.error == 0) {
        res.content.prescriptionCreateTime = res.content.prescriptionCreateTime.substr(0, 10);
        if(res.content.patientSex == 0){
          res.content.patientSexName = '男'
        }else{
          res.content.patientSexName = '女'
        }
        this.data.prescriptionInfo = res.content;
        this.setData({
          prescriptionInfo: this.data.prescriptionInfo
        })
      } else {
        util.showModal('提示', res.message)
        return false
      }
    }, {prescriptionCode: this.data.prescriptionCode})
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