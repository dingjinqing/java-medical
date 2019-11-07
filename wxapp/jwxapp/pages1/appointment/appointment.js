// pages1/appointment/appointment.js
let util = require('../../utils/util')
let app = getApp()
let imageUrl = app.globalData.imageUrl;
let a = require('../../utils/util')

Page({
  /**
   * 页面的初始数据
   */
  data: {
    serviceId: '',
    imageUrl: imageUrl,
    img_cart: imageUrl + '/image/wxapp/cart_icon.png',
    img_store: imageUrl + 'image/wxapp/sto_logo1.png',
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    img_addr: imageUrl + '/image/wxapp/store_address.png',
    img_time: imageUrl + '/image/wxapp/store_time.png',
    img_charge: imageUrl + '/image/wxapp/icon_notice.png',
    img_arrow: imageUrl + '/image/wxapp/backward_right.png',
    img_sercer: imageUrl + '/image/wxapp/server_icon.png',
    img_success: imageUrl + '/image/wxapp/con_btn_success.png',
    img_iconsel: imageUrl + '/image/wxapp/selected.png',
    img_service: imageUrl + 'image/wxapp/icon_service.png',

    storeInfo: {}, // 门店信息
    serviceInfo: {}, // 门店服务信息
    reservationInfoList: [], // 门店服务可预约时间详情

    tech_name: '', // 技师名称
    commentInfo: [], // 评论

    timeMode: false, // 预约时间弹窗
    show_id: 0
  },

  /**
   * 
   */
  showMap() { },

  /**
   * 选择预约时间
   */
  timeShow() {
    this.setData({
      timeMode: true
    })
  },

  /**
   * 
   */
  powerDrawer() {

  },

  /**
   * 技师
   */
  techShow() { },

  /**
   * 评价
   */
  click_to_detail() { },

  toBack() {
    util.showModal('提示', '找不到该服务', function () {
      wx.navigateBack()
    })
  },

  /**
   * 立即预约
   */
  OneClickBuy() { },

  /**
   * 点击评论图片
   */
  clickComment() { },

  /**
   * 点击预约时间
   */
  dateClick(e) {
    let key = e.currentTarget.dataset.key
    this.setData({
      show_id: key
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    let serviceId = options.service_id
    if (!serviceId) {
      that.toBack()
      return false
    }
    that.setData({
      serviceId: serviceId
    })
    util.api('/api/wxapp/store/service/reservation', function (res) {
      if (res.error === 0) {
        console.log(res.content)
        let serviceInfo = res.content.serviceInfo
        let storeInfo = res.content.storeInfo
        let reservationInfoList = res.content.reservationInfoList
        serviceInfo.serviceImg = JSON.parse(serviceInfo.serviceImg)
        if (a) {
          console.log(a)
          // console.log(a.a.content.reservationInfoList)
          reservationInfoList = a.a.content.reservationInfoList
        }
        that.setData({
          reservationInfoList: reservationInfoList,
          storeInfo: storeInfo,
          serviceInfo: serviceInfo
        })
      } else {
        that.toBack()
      }
    }, { serviceId: serviceId })
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