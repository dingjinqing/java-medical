// pages1/returnlogistics/returnlogistics.js
var util = require('../../utils/util.js');
var app = getApp();
var imageUrl = app.globalData.imageUrl;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    click_look: imageUrl + 'image/wxapp/click_look.png',
    add_img: imageUrl + '/image/wxapp/return_img_icom.png',
    dele_service: imageUrl + '/image/admin/dele_service.png',
    orderSn: '', // 订单号
    orderId: '', // 订单id
    returnSn: '', // 退货单号
    retId: '', // 退款订单id
    orderInfo: {}, // 订单信息
    uploadedImg: [], // 凭证图片
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let orderSn = options.order_sn
    let orderId = options.order_id
    let returnSn = options.return_sn
    let retId = options.ret_id
    this.setData({
      orderSn: orderSn,
      orderId: orderId,
      returnSn: returnSn,
      retId: retId
    })
    this.initData()
  },

  initData () {
    let that = this
    util.api('/api/wxapp/order/refund/info', function (res) {
      if (res.error === 0) {
        let orderInfo = res.content
        let refundStatus = Number(orderInfo.refundStatus)
        let returnType = Number(orderInfo.returnType)
        that.setData({
          orderInfo: orderInfo,
          refundStatus: refundStatus,
          returnType: returnType
        })
      }
    }, { returnOrderSn: that.data.returnSn })
  },

  uploadRefundImg () {
    let that = this
    let uploadedImg = that.data.uploadedImg
    util.uploadImage(1, function (res) {
      if (res.data) {
        let data = JSON.parse(res.data)
        if (data.error === 0) {
          let img = data.content
          if (uploadedImg.length < 3) {
            uploadedImg.push(img)
            that.setData({
              uploadedImg: uploadedImg
            })
          }
        }
      }
    })
  },
  submitReturnLogistics () {
    let params = {
      orderId: this.data.orderId,
      retId: this.data.retId,
      orderSn: that.data.orderSn,
      action: 1,
      returnOperate: 0,
      shippingType: that.data.orderInfo.shippingType,
      shippingNo: that.data.orderInfo.shippingNo,
      phone: that.data.orderInfo.phone,
      voucherImages: JSON.stringify(that.data.uploadedImg)
    }
    util.api('/api/wxapp/order/refund', function (res) {
      if (res.error === 0) {
        util.success_toast(res.message)
      } else {
        util.fail_toast(res.message)
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