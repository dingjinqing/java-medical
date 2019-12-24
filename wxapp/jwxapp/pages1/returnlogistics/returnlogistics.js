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
    couriers: [], // 快递公司列表
    courierIndex: 0,
    shippingNo: '', // 物流号
    phone: '', // 手机号
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let returnSn = options.return_sn
    this.setData({
      returnSn: returnSn
    })
    this.initData()
    this.initCouriers()
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

  // 快递公司列表
  initCouriers () {
    let that = this
    util.api('/api/wxapp/order/express', function (res) {
      if (res.error === 0) {
        that.setData({
          couriers: res.content
        })
      }
    })
  },

  // 填写物流号
  shippingNoBlur (e) {
    let value = e.detail.value
    if (!value) {
      util.showModal('提示', '请填写运单号码！')
    }
    this.setData({
      shippingNo: value
    })
  },

  // 填写手机号
  phoneBlur (e) {
    let value = e.detail.value
    if (!value) {
      util.showModal('提示', '请填写手机号！')
    }
    if (/^1[3456789]\d{9}$/.test(e.detail.value)) {
      this.setData({
        phone: e.detail.value
      })
    } else {
      util.showModal('提示', "请输入正确的手机号！");
      this.setData({
        phone: ''
      })
    }
  },

  // 上传凭证图片
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

  // 删除图片
  delImage (e) {
    let index = e.currentTarget.dataset.idx
    this.data.uploadedImg.splice(index, 1)
    this.setData({
      uploadedImg: this.data.uploadedImg
    })
  },

  // 提交物流
  submitReturnLogistics () {
    let that = this
    let params = {
      orderId: this.data.orderInfo.orderId,
      retId: this.data.orderInfo.retId,
      orderSn: that.data.orderInfo.orderSn,
      action: 1,
      returnOperate: 0,
      shippingType: that.data.couriers[that.data.courierIndex].shippingId,
      shippingNo: that.data.shippingNo, // 物流单号
      phone: that.data.phone,
      voucherImages: '',
      returnType: that.data.orderInfo.returnType
    }
    if (!params.shippingNo) {
      util.showModal('提示', '请填写运单号码！')
      return false;
    }
    if (!params.phone) {
      util.showModal('提示', '请填写手机号码！')
      return false;
    }
    if (!/^1[3456789]\d{9}$/.test(params.phone)) {
      util.showModal('提示', "请输入正确的手机号！");
      return false;
    }
    if (that.data.uploadedImg.length > 0) {
      let voucherImages = that.data.uploadedImg.map(item => item.imgPath)
      params.voucherImages = JSON.stringify(voucherImages)
    }
    console.log(params)
    util.api('/api/wxapp/order/refund', function (res) {
      if (res.error === 0) {
        util.toast_success(res.message)
        util.navigateTo({
          url: '/pages1/returndetail/returndetail?return_sn=' + that.data.returnSn
        })
      } else {
        util.toast_fail('抱歉，提交失败！')
      }
    }, params)
  },

  // 物流公司改变
  bindCourierChange (e) {
    let value = e.detail.value
    this.setData({
      courierIndex: value
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