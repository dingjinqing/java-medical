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
        util.showModal('提示', res.message)
      }
    }, { orderSn: orderSn })
  },
  // 预约评价
  toComment: function (e) {
    let orderSn = e.currentTarget.dataset.ordersn;
    let storeId = e.currentTarget.dataset.storeid;
    util.navigateTo({
      url: '../servicecomment/servicecomment?orderSn=' + orderSn + "&storeId=" + storeId
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
    let form_id = e.detail.formId;
    let open_id = util.getCache("openid");
    let that = this;
    util.showModal('提示', '是否取消该订单', function () {
      util.api('/api/wxapp/service/cancel', function (res) {
        if (res.error == 0) {
          orderInfo.orderStatus = 1;
          that.setData({
            orderInfo: orderInfo
          })
        } else if (res.error == 400002) {

        }
      }, { orderSn: orderSn, open_id: open_id, form_id: form_id })
    }, true);

  },
  // 取消预约
  toCancel: function (e) {
    let mobile = e.currentTarget.dataset.mobile;
    util.showModal('提示', '请与商家联系后，由商家取消', function () {
      wx.makePhoneCall({
        phoneNumber: mobile
      })
    }, true, '取消', '直接联系');
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
    util.api('/api/wxapp/service/pay', function (res) {
      if (res.error == 0) {
        if (typeof (res.content.timeStamp) != 'undefined') {
          wx.requestPayment({
            'timeStamp': res.content.timeStamp,
            'nonceStr': res.content.nonceStr,
            'package': res.content.package,
            'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
            'paySign': res.content.paySign,
            'success': function (res) {
              util.toast_success('支付成功');
              util.navigateTo({
                url: '/pages/appointinfo/appointinfo?orderSn=' + orderSn,
              })
            },
            'fail': function (res) {
              util.toast_fail('支付失败');
              util.navigateTo({
                url: '/pages/appointinfo/appointinfo?orderSn=' + orderSn,
              })
            },
            'complete': function (res) {
            }
          });
        } else {
          util.toast_fail('支付失败');
          util.redirectTo({
            url: '/pages/appointinfo/appointinfo?orderSn=' + orderSn,
          })
        }
      } else if (e.error == 400002) {
        util.showModal('提示', e.content, function () {
          wx.navigateBack();
        });
      } else {
        util.showModal("提示", res.message, function () {
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