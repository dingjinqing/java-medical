// pages1/prescriptionlist/prescriptionlist.wxml.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    pageParams: null,
    dataList: null,
    patientId: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.data.patientId = options.patientId || 1;
    this.requestList();
  },
  to_pre () {
    util.jumpLink('/pages1/getprescription/getprescription')
  },
  to_info (e) {
    util.jumpLink('/pages1/prescriptioninfo/prescriptioninfo?prescriptionCode=' + e.currentTarget.dataset.prescriptionno)
  },  
  requestList () {
    let currentPage = this.data.pageParams ? this.data.pageParams.currentPage : 1;
    util.api('/api/wxapp/prescription/list', res => {
      if (res.error == 0) {
        console.log(res);
        let dataList = JSON.stringify(res.content.dataList);
        dataList = JSON.parse(dataList)
        console.log(dataList)
        for (let i in dataList) {
          dataList[i].prescriptionCreateTime = dataList[i].prescriptionCreateTime.substr(0,10)
        }
        this.setData({
          pageParams: res.content.page,
          ['dataList[' + (parseInt(currentPage) - 1) + ']']: dataList
        });
      } else {
        util.showModal('提示',res.message)
      }
    },{currentPage: currentPage, pageRows: 20, patientId: this.data.patientId })
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
    if (this.data.pageParams && this.data.pageParams.currentPage === this.data.pageParams.lastPage )
      return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})