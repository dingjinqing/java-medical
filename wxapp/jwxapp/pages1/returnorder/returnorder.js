// pages1/returnorder/returnorder.js
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
    type_active: imageUrl + 'image/wxapp/con_btn_success.png',
    click_look: imageUrl + 'image/wxapp/click_look.png',
    add_img: imageUrl + '/image/wxapp/return_img_icom.png',
    dele_service: imageUrl + '/image/admin/dele_service.png',
    orderInfo: {},
    activityName: '',
    goodsType: '',
    returnType: 0, // 选择的操作:1退款退货、0仅退款、4换货
    orderSn: '', // 订单号
    orderId: '', // 订单id
    returnTypes: [], // 本订单支持的操作
    goodsInfo: [], // 商品信息
    selectGoodIds: [], // 选择的商品
    reasone: i18n.trans("page1.afterSale.reasone"), // 退货退款原因
    reasone_huan: i18n.trans("page1.afterSale.reasone_huan"), // 换货原因
    reasoneIndex: 0, // 选中的原因
    returnMoney: 0.00, //退款金额
    uploadedImg: [], // 已经上传的图片
    reasonDesc: '', // 申请说明
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
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
                  name: that.$t("page1.afterSale.return[0]")
                })
              } else if (index === 1) {
                supportTypes.push({
                  id: 1,
                  name: that.$t("page1.afterSale.return[1]")
                })
              } else if (index === 4) {
                supportTypes.push({
                  id: 4,
                  name: that.$t("page1.afterSale.return[4]")
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
            item.goodsImg = 'image/wxapp/no_order.png'
          }
        })
        // 商品活动
        let activityName = '', goodsType = '';
        if (orderInfo.orderInfo && orderInfo.orderInfo.goodsType) {
          let goodsTypes = orderInfo.orderInfo.goodsType.split(',')
          for (let i = 0; i < goodsTypes.length; i++) {
            let type = goodsTypes[i]
            goodsType = type
            switch (type) {
              case 1:
                activityName = that.$t("page1.afterSale.activityName[0]")
                break
              case 3:
                activityName = that.$t("page1.afterSale.activityName[1]")
                break
              case 5:
                activityName = that.$t("page1.afterSale.activityName[2]")
                break
              default:
                activityName = ''
                break
            }
          }
        }
        that.setData({
          returnTypes: supportTypes,
          orderInfo: orderInfo,
          goodsInfo: goodsInfo ? goodsInfo : [],
          activityName: activityName,
          goodsType: goodsType
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
        util.toast_success(that.$t("page1.afterSale.contentCopied"))
      }
    })
  },

  // 切换售后类型
  toggleType (e) {
    let id = e.currentTarget.dataset.id
    if (id === this.data.returnType) return false
    this.setData({
      returnType: id
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

  reasonDescInput (e) {
    let value = e.detail.value
    this.setData({
      reasonDesc: value
    })
  },

  // 上传凭证
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

  delImage (e) {
    let index = e.currentTarget.dataset.idx
    let uploadedImg = this.data.uploadedImg
    uploadedImg.splice(index, 1)
    this.setData({
      uploadedImg: uploadedImg
    })
  },

  // 提交退款退货申请
  submitRefund () {
    let that = this
    let selectGoods = that.data.goodsInfo.filter(data => data.checked).map(item => {
      return {
        recId: item.recId,
        returnNumber: item.returnable, // 可退数量
        money: item.discountedGoodsPrice
      }
    })
    let uploadedImgs = that.data.uploadedImg.map(item => {
      return item.imgPath
    })
    let params = {
      orderId: that.data.orderId,
      orderSn: that.data.orderSn,
      action: 1, // 查询类型：0.发货查询 1.退款退货查询
      returnType: that.data.returnType,
      returnMoney: that.data.returnMoney,
      shippingFee: 0,// 退运费金额
      reasonType: that.data.reasoneIndex, // 申请原因
      reasonDesc: that.data.reasonDesc, // 申请说明
      voucherImages: JSON.stringify(uploadedImgs),// 凭证图片
      returnGoods: selectGoods
    }
    console.log(params)
    if (selectGoods.length === 0) {
      util.showModal(that.$t("page1.afterSale.prompt"), that.$t("page1.afterSale.sProduct"))
      return false
    }
    util.api('/api/wxapp/order/refund', function (res) {
      if (res.error === 0) {
        let content = res.content
        util.toast_success(that.$t("page1.afterSale.successApply"))
        util.navigateTo({
          url: '/pages1/returndetail/returndetail?return_sn=' + content
        })
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