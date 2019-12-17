// pages1/returnorder/returnorder.js
var util = require('../../utils/util.js');
var app = getApp();
var imageUrl = app.globalData.imageUrl;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    type_active: imageUrl + 'image/wxapp/con_btn_success.png',
    click_look: imageUrl + 'image/wxapp/click_look.png',
    add_img: imageUrl + '/image/wxapp/return_img_icom.png',
    orderInfo: {
      activityType: 1
    },
    orderType: 0, // 选择的操作:退款退货、仅退款、换货
    orderSn: '', // 订单号
    orderId: '', // 订单id
    orderTypes: [], // 本订单支持的操作
    goodsInfo: [], // 商品信息
    selectGoodIds: [], // 选择的商品
    reasone: ['协商一致退款', '未按约定时间发货', '缺货', '多拍/不想要', '其他'], // 退货退款原因
    reasone_huan: ['协商一致换货', '商品与页面描述不符', '发错货', '商品损坏', '其他'], // 换货原因
    reasoneIndex: 0, // 选中的原因
    returnMoney: 0.00, //退款金额
    uploadedImg: [], // 已经上传的图片
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    let orderSn = options.order_sn
    let orderId = options.order_id
    this.setData({
      orderSn: orderSn,
      orderId: orderId
    })
    if (orderSn) {
      this.initRefundInfo()
    }
  },

  // 初始化退款、退货信息
  initRefundInfo () {
    let that = this
    util.api('/api/wxapp/order/refund/query', function (res) {
      if (res.error === 0) {
        let orderInfo = res.content
        let supportTypes = []
        // 本单支持的操作
        if (orderInfo.returnType && orderInfo.returnType.length > 0) {
          orderInfo.returnType.forEach((item, index) => {
            if (item) {
              if (index === 0) {
                supportTypes.push({
                  id: 0,
                  name: '仅退款'
                })
              } else if (index === 1) {
                supportTypes.push({
                  id: 1,
                  name: '退货/退款'
                })
              } else if (index === 4) {
                supportTypes.push({
                  id: 4,
                  name: '换货'
                })
              }
            }
          })
        }
        // 选中状态
        let goodsInfo = orderInfo.refundGoods || []
        goodsInfo.forEach(item => {
          item.checked = false
          if (!item.goodsImg) {
            item.goodsImg = 'upload/245547/image/20191212/plaHOXplJV1NGrHmS6J6.jpg'
          }
        })
        that.setData({
          orderTypes: supportTypes,
          orderInfo: orderInfo,
          goodsInfo: goodsInfo ? goodsInfo : []
        })
      }
    }, {
      orderSn: that.data.orderSn,
      orderId: that.data.orderId,
      action: 1
    })
  },

  // 复制订单号
  copyOrder () {
    let that = this
    wx.setClipboardData({
      data: that.data.orderSn,
      success: function (res) {
        util.toast_success('内容已复制')
      }
    })
  },

  // 切换售后类型
  toggleType (e) {
    let id = e.currentTarget.dataset.id
    if (id === this.data.orderType) return false
    this.setData({
      orderType: id
    })
  },

  // 切换商品多选框选中状态
  toggleGoodsSelect (e) {
    let id = e.currentTarget.dataset.sku
    let index = e.currentTarget.dataset.index
    this.data.goodsInfo[index].checked = !this.data.goodsInfo[index].checked
    this.computedRetureMoney()
    this.setData({
      goodsInfo: this.data.goodsInfo
    })
  },

  // 计算退款金额
  computedRetureMoney () {
    let goodsInfo = this.data.goodsInfo
    let returnMoney = 0
    goodsInfo.forEach((item, index) => {
      if (item.checked) {
        returnMoney += item.discountedGoodsPrice * item.returnable
      }
    })
    this.setData({
      returnMoney: returnMoney
    })
  },

  // 退货原因切换后回调
  bindPickerChange (e) {
    let index = e.detail.value
    this.setData({
      reasoneIndex: index
    })
  },

  // 上传凭证
  uploadRefundImg () {
    console.log('upload')

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