// APPPONTINFO.JS 2018.03.12
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
    img_code: imageUrl+"image/wxapp/xy_61.png",
    prd_img: imageUrl + "image/wxapp/address.png",
    img_store: imageUrl + "image/wxapp/address.png",
    img_arrow: imageUrl + "image/wxapp/click_look.png",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    var order_sn = options.order_sn;
    util.api('api/wxapp/service/orderdetail', function (e) {
       order_info = e.content;
       lat = Number(order_info.latitude);
       lon = Number(order_info.longitude);
       order_info.service_img = JSON.parse(order_info.service_img);
       order_info.main_imgs = order_info.service_img[1];
       order_info.store_img = JSON.parse(order_info.store_img);
       if (order_info.store_img != null)
       order_info.main_imgss = order_info.store_img[0];
       order_info.verify_code_img = qrcode.createQrCodeImg(order_info.verify_code, { 'size': 300 });
      that.setData({
        order_info: order_info
      })
    }, {
      order_sn: order_sn})
  },
  ser_comment: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    var store_id = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '../servicecomment/servicecomment?order_sn=' + order_sn + "&store_id=" + store_id
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  showMap: function (e) {
    var address = order_info.address
    wx.openLocation({
      latitude: lat,
      longitude: lon,
      scale: 28,
      name:address
    })
  },
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
    },true);

  },
  toCancel: function(e){
    var mobile = e.currentTarget.dataset.mobile;
    util.showModal('提示', '请与商家联系后，由商家取消', function () {
      wx.makePhoneCall({
        phoneNumber: mobile
      })
    }, true, '取消', '直接联系');
  },
  toConfirm: function (e) {
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    var order_sn = e.currentTarget.dataset.order_sn;
    var order_id = order_info.order_id;
    util.api('/api/wxapp/service/finished',function(res){
      if (res.error == 0) {
        order_info.order_status = 2;
        that.setData({
          order_info: order_info
        })
      }
    }, {   order_id: order_id, act: 2,open_id:open_id,form_id:form_id })
  },
  goStore:function(e){
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
  //去支付
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
      }else{
        util.showModal("提示",res.message,function(){
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
})
