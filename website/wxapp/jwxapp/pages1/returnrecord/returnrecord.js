// pages1/returnrecord/returnrecord.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var orderInfo = [];

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    orderSn: '',
    orderId: '',
    returnSn: '',
    orderInfo: {},
    can_shipping_fee: 0,
    activityName: '',
    goodsType: '' // 活动类型
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    this.setData({
      orderSn: options.order_sn,
      orderId: options.order_id,
      returnSn: options.return_sn
    })
    // util.api('/api/wxapp/return/change', function (res) {
    //   if (res.error == 0) {
    //     orderInfo = res.content;
    //     var can_shipping_fee = '0.00';
    //     if (orderInfo.order.order_status == 3) {
    //       can_shipping_fee = orderInfo.can_return_free != undefined ? orderInfo.can_return_free : 0;
    //     }
    //     orderInfo.order.type_icon = ''
    //     orderInfo.order.goods_type = orderInfo.order.goods_type.split(",");
    //     for (var j in orderInfo.order.goods_type) {
    //       if (orderInfo.order.goods_type[j] == 1) {
    //         orderInfo.order.type_icon = "拼团"
    //       } else if (orderInfo.order.goods_type[j] == 3) {
    //         orderInfo.order.type_icon = '砍价'
    //       } else if (orderInfo.order.goods_type[j] == 5) {
    //         orderInfo.order.type_icon = "秒杀"
    //       } else {
    //         orderInfo.order.type_icon == ""
    //       }
    //     }
    //     that.setData({
    //       orderInfo: orderInfo,
    //       can_shipping_fee: can_shipping_fee,
    //     })
    //   }
    // }, { order_sn: order_sn, ret_id: ret_id })
    this.initData()
  },

  initData () {
    let that = this
    util.api('/api/wxapp/order/refund/info', function (res) {
      if (res.error === 0) {
        orderInfo = res.content
        // 商品活动
        let activityName = '', goodsType = '';
        if (orderInfo.orderInfo && orderInfo.orderInfo.goodsType) {
          let goodsTypes = orderInfo.orderInfo.goodsType.split(',')
          for (let i = 0; i < goodsTypes.length; i++) {
            let type = goodsTypes[i]
            goodsType = type
            switch (type) {
              case 1:
                activityName = that.$t('page1.afterSale.activityName[0]')
                break
              case 3:
                activityName = that.$t('page1.afterSale.activityName[1]')
                break
              case 5:
                activityName = that.$t('page1.afterSale.activityName[2]')
                break
              default:
                activityName = ''
                break
            }
          }
        }
        that.setData({
          orderInfo: orderInfo,
          activityName: activityName,
          goodsType: goodsType
        })
      }
    }, { returnOrderSn: this.data.returnSn })
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