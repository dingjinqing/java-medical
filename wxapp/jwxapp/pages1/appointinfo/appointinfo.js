// 预约订单详情：pages1/appointinfo/appointinfo.js
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
var qrcode = require("../../utils/qrcode.js");
var order_info = [];
var lat;
var lon;
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
    order_info: {}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    var order_sn = options.order_sn;
    util.api('/api/wxapp/store/service/reservationDetail', function (res) {
      console.log('res:', res)
      // order_info = e.content;
      let order_info = {
        order_sn: 1,
        add_time: '2019-11-14 10:28:00',
        service_img: '["http://jmpdevimg.weipubao.cn/upload/245547/image/20191106/Cw7hweKwF9TZTt3M0JBZ.jpg","http://jmpdevimg.weipubao.cn/upload/245547/image/20191106/piHajXgTBcS0EwFv5LyK.jpg","http://jmpdevimg.weipubao.cn/upload/245547/image/20191106/6zQd7CS4Briz5NWQ4OH6.jpg"]',
        service_name: "辅导功课",
        service_id: '1',
        servicePrice: 100,
        serviceShelf: 1,
        serviceSn: "G101011239",
        serviceSubsist: 20,
        serviceType: 0,
        servicesNumber: 10,
        startDate: "2019-11-07",
        startPeriod: "09:00",
        techServicesNumber: null,
        updateTime: "2019-11-13 16:34:06",
        shopAvatar: "upload/1/image/20190903/8quev7YQPROAdfBXQiIx.jpg",
        storePojo: { storeId: 6, storeName: "牡丹园门店", manager: "zzz", mobile: "15010607187" },
        store_img: '["http://mpdevimg2.weipubao.cn/upload/0/image/20190927/crop_krznocD6sNDo1zIQ.jpeg"]',
        technicianTitle: "伏虎罗汉",
        verify_code: 'http://baidu.com',
        technician_title: '伏虎罗汉',
        technician_name: '小手张',
        service_date: '2019-11-07',
        service_period: '09:00-19:00',
        address: "北京南站",
        latitude: "39.865078",
        longitude: "116.378929",
        order_status: 0,
        money_paid: 10,
        service_price: 100,
        store_id: 6,
        store_name: '牡丹园门店',
        error: 0,
        language: "zh_CN",
        message: "成功"
      }
      lat = Number(order_info.latitude);
      lon = Number(order_info.longitude);
      order_info.service_img = JSON.parse(order_info.service_img);
      order_info.main_imgs = order_info.service_img[1];
      order_info.store_img = JSON.parse(order_info.store_img);
      if (order_info.store_img != null) order_info.main_imgss = order_info.store_img[0];
      order_info.verify_code_img = qrcode.createQrCodeImg(order_info.verify_code, { 'size': 300 });
      that.setData({
        order_info: order_info
      })
    }, { orderSn: order_sn })
  },
  // 预约评价
  ser_comment: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    var store_id = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '../servicecomment/servicecomment?order_sn=' + order_sn + "&store_id=" + store_id
    })
  },
  showMap: function (e) {
    var address = order_info.address
    wx.openLocation({
      latitude: lat,
      longitude: lon,
      scale: 28,
      name: address
    })
  },
  // 取消预约
  toTrueCancel: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    var that = this;
    util.showModal('提示', '是否取消该订单', function () {
      util.api('/api/wxapp/service/cancel', function (res) {
        if (res.error == 0) {
          order_info.order_status = 1;
          that.setData({
            order_info: order_info
          })
        } else if (res.error == 400002) {

        }
      }, { order_sn: order_sn, open_id: open_id, form_id: form_id })
    }, true);

  },
  // 取消预约
  toCancel: function (e) {
    var mobile = e.currentTarget.dataset.mobile;
    util.showModal('提示', '请与商家联系后，由商家取消', function () {
      wx.makePhoneCall({
        phoneNumber: mobile
      })
    }, true, '取消', '直接联系');
  },
  // 确认完成
  toConfirm: function (e) {
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    var order_sn = e.currentTarget.dataset.order_sn;
    var order_id = order_info.order_id;
    util.api('/api/wxapp/service/finished', function (res) {
      if (res.error == 0) {
        order_info.order_status = 2;
        that.setData({
          order_info: order_info
        })
      }
    }, { order_id: order_id, act: 2, open_id: open_id, form_id: form_id })
  },
  goStore: function (e) {
    var store_ids = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '/pages/storeinfo/storeinfo?id=' + store_ids
    })
  },
  toDetail: function (e) {
    var service_id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/appointment/appointment?service_id=' + service_id
    })
  },
  // 去支付
  toPay: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    var openid = util.getCache('openid');
    var form_id = e.detail.formId;
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
                url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
              })
            },
            'fail': function (res) {
              util.toast_fail('支付失败');
              util.navigateTo({
                url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
              })
            },
            'complete': function (res) {
            }
          });
        } else {
          util.toast_fail('支付失败');
          util.redirectTo({
            url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
          })
        }
      } else if (e.error == 400002) {
        util.showModal('提示', e.content, function () {
          wx.navigateBack();
        });
      } else {
        util.showModal("提示", res.message, function () {
          wx.navigateBack({});
        });
      }
    }, { order_sn: order_sn, openid: openid, form_id: form_id })
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