// pages1/returnorderlist/returnorderlist.js
var util = require('../../utils/util.js');
var i18n = require("../../utils/i18n/i18n.js")
var app = getApp();
var imageUrl = app.globalData.imageUrl;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    click_look: imageUrl + 'image/wxapp/click_look.png',
    orderSn: '',
    orderId: '',
    returnFlag: 0, // 是否展示创建售后申请按钮
    returnOrderList: [], // 售后单列表
    createTime: '', // 下单时间
    canReturnShippingFee: '', // 还可以退运费
    return: i18n.trans("page1.afterSale.return"), // 售后类型
    reasone: i18n.trans("page1.afterSale.reasone"), // 退货退款原因
    reasone_huan: i18n.trans("page1.afterSale.reasone_huan"), // 换货原因

    voucherImages: [], // 物流凭证
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let orderSn = options.order_sn
    this.setData({
      orderSn: orderSn,
      orderId: options.order_id ? options.order_id : ''
    })
    this.initData()
  },

  initData () {
    let that = this
    util.api('/api/wxapp/order/refund/list', function (res) {
      if (res.error === 0) {
        let content = res.content
        content.returnOrderlist.forEach(function (item) {
          if (item.voucherImages) {
            item.voucherImages = JSON.parse(item.voucherImages)
          }
        })
        that.setData({
          canReturnShippingFee: content.canReturnShippingFee,
          orderSn: content.orderSn,
          returnFlag: content.returnFlag,
          returnOrderList: content.returnOrderlist,
          createTime: content.createTime
        })
      }
    }, {
      orderSn: that.data.orderSn
    })
  },

  // 查看售后详情
  toDetail (e) {
    console.log(e)
    let orderSn = this.data.orderSn
    let order = e.currentTarget.dataset
    let returnOrderSn = order.return_sn
    let refundStatus = order.order_status
    let returnType = order.order_type
    util.navigateTo({
      url: '/pages1/returndetail/returndetail?return_sn=' + returnOrderSn
    })
  },

  // 创建售后申请
  createReturnOrder (e) {
    util.navigateTo({
      url: '/pages1/returnorder/returnorder?order_sn=' + this.data.orderSn + '&order_id=' + this.data.orderId
    })
  },

  // 提交物流
  returnOrder (e) {
    let returnOrderSn = e.currentTarget.dataset.return_sn
    util.navigateTo({
      url: '/pages1/returnlogistics/returnlogistics?return_sn=' + returnOrderSn
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