// pages1/appointorder/appointorder.js
const util = require('../../utils/util.js')
let app = getApp()
let imageUrl = app.globalData.imageUrl;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    userId: '',
    userName: '',
    mobile: '',
    reserveInfo: {},

    imageUrl: imageUrl,
    images: {},
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
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    console.log(app)
    let reserveInfo = JSON.parse(options.data)
    let userId = util.getCache('user_id')
    let userName = util.getCache('nickName')
    let mobile = util.getCache('mobile')
    console.log(reserveInfo)
    this.setData({
      userId: userId,
      userName: userName,
      mobile: mobile,
      reserveInfo: reserveInfo
    })
    this.initData()
  },

  initData() {
    let params = {
      serviceId: this.data.reserveInfo.serviceId,
      userId: this.data.userId
    }
    util.api('/api/wxapp/store/service/confirmReservation', function (res) {
      console.log(res)
      if (res.error === 0) {
        let content = res.content
      }
    }, params)
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