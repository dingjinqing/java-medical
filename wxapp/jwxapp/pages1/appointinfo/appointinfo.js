// 预约订单详情：pages1/appointinfo/appointinfo.js
let app = new getApp();
let imageUrl = app.globalData.imageUrl;
let util = require('../../utils/util.js');
let qrcode = require("../../utils/qrcode.js");
let orderInfo = [];
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    img_code: imageUrl + "image/wxapp/xy_61.png",
    prd_img: imageUrl + "image/wxapp/address.png",
    img_store: imageUrl + "image/wxapp/address.png",
    img_arrow: imageUrl + "image/wxapp/click_look.png",
    orderInfo: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    let that = this;
    let orderSn = options.order_sn;
    util.api('/api/wxapp/store/service/reservationDetail', function (res) {
      if (res.error === 0) {
        let orderInfo = res.content;
        orderInfo.verifyCodeImg = qrcode.createQrCodeImg(orderInfo.verifyCode, { 'size': 300 });
        that.setData({
          orderInfo: orderInfo
        })
      } else {
        util.showModal(that.$t('page1.reserve.prompt'), res.message)
      }
    }, { orderSn: orderSn })
  },
  // 预约评价
  toComment: function (e) {
    let orderSn = e.currentTarget.dataset.ordersn;
    let storeId = e.currentTarget.dataset.storeid;
    util.navigateTo({
      url: '../servicecomment/servicecomment?order_sn=' + orderSn + "&store_id=" + storeId
    })
  },
  showMap: function (e) {
    let address = orderInfo.address
    wx.openLocation({
      latitude: Number(this.data.orderInfo.latitude),
      longitude: Number(this.data.orderInfo.longitude),
      scale: 28,
      name: address
    })
  },
  // 取消预约
  toTrueCancel: function (e) {
    let orderSn = e.currentTarget.dataset.ordersn;
    let orderId = e.currentTarget.dataset.order_id;
    let form_id = e.detail.formId;
    let open_id = util.getCache("openid");
    let that = this;
    util.showModal(that.$t('page1.reserve.prompt'), that.$t('page1.reserve.whetherToCancelOrder'), function () {
      util.api('/api/wxapp/store/service/cancelReservation', function (res) {
        if (res.error == 0) {
          orderInfo.orderStatus = 2;
          that.setData({
            orderInfo: orderInfo
          })
        } else if (res.error == 400002) {

        }
      }, { orderId: orderId, cancelReason: that.$t('page1.reserve.reasonForCancellation'), orderSn: orderSn, open_id: open_id, form_id: form_id })
    }, true);

  },
  // 取消预约
  toCancel: function (e) {
    let mobile = e.currentTarget.dataset.mobile;
    util.showModal(this.$t('page1.reserve.prompt'), this.$t('page1.reserve.contactMerchant'), function () {
      wx.makePhoneCall({
        phoneNumber: mobile
      })
    }, true, $t('page1.reserve.cancel2'), $t('page1.reserve.contactDirectly'));
  },
  // 确认完成
  toConfirm: function (e) {
    let that = this;
    let form_id = e.detail.formId;
    let orderId = this.data.orderInfo.orderId;
    let userId = util.getCache('user_id')
    util.api('/api/wxapp/store/service/comfirmComplete', function (res) {
      if (res.error == 0) {
        let orderInfo = res.content
        orderInfo.verifyCodeImg = qrcode.createQrCodeImg(orderInfo.verifyCode, { 'size': 300 });
        that.setData({
          orderInfo: orderInfo
        })
      }
    }, { orderId: orderId, userId: userId, form_id: form_id })
  },
  goStore: function (e) {
    let storeIds = e.currentTarget.dataset.storeid;
    util.navigateTo({
      url: '/pages/storeinfo/storeinfo?id=' + storeIds
    })
  },
  toDetail: function (e) {
    let serviceId = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/appointment/appointment?serviceId=' + serviceId
    })
  },
  // 去支付
  toPay: function (e) {
    let orderSn = e.currentTarget.dataset.ordersn;
    let openid = util.getCache('openid');
    let form_id = e.detail.formId;
    let that = this;
    util.api('/api/wxapp/store/service/reservationContinuePay', function (res) {
      if (res.error == 0) {
        if (typeof (res.content.timeStamp) != 'undefined') {
          wx.requestPayment({
            'timeStamp': res.content.timeStamp,
            'nonceStr': res.content.nonceStr,
            'package': res.content.package,
            'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
            'paySign': res.content.paySign,
            'success': function (res) {
              util.toast_success(that.$t('page1.reserve.paymentSuccessful'));
              util.navigateTo({
                url: '/pages/appointinfo/appointinfo?order_sn=' + orderSn,
              })
            },
            'fail': function (res) {
              util.toast_fail(that.$t('page1.reserve.paymentFailed'));
              util.navigateTo({
                url: '/pages/appointinfo/appointinfo?order_sn=' + orderSn,
              })
            },
            'complete': function (res) {
            }
          });
        } else {
          util.toast_fail(that.$t('page1.reserve.paymentFailed'));
          util.redirectTo({
            url: '/pages/appointinfo/appointinfo?order_sn=' + orderSn,
          })
        }
      } else if (e.error == 400002) {
        util.showModal(that.$t('page1.reserve.prompt'), e.content, function () {
          wx.navigateBack();
        });
      } else {
        util.showModal(that.$t('page1.reserve.prompt'), res.message, function () {
          wx.navigateBack();
        });
      }
    }, { orderSn: orderSn, openid: openid, form_id: form_id })
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})