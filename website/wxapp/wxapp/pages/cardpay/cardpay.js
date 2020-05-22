// pages/cardpay/cardpay.js
var app = getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
var ipt_money = 0;
var recharge_arr = [];
var offset_arr = [];
var card_id;
var card_no;
var money_arr = {};
var charge_money = [];
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (!util.check_setting(options)) return;
    card_id = options.card_id;
    ipt_money = 0;
    var that = this;
    wx.getSystemInfo({
      success: function(res) {
        that.setData({
          windowHeight: res.windowHeight
        })
      }
    })
    util.api('api/card/charge/rule', function(res) {
      if (res.error == 0) {
        var info = res.content;
        card_no = info.card_no;
        info.charge_money = JSON.parse(info.charge_money);
        info.offset_momey = info.charge_money[0].value;
        offset_arr = info.charge_money[0];
        recharge_arr = [];
        charge_money = [];
        charge_money.push(offset_arr);
        if (info.offset_momey == 0) {
          for (var i = 1; i < (info.charge_money.length - 1); i++) {
            recharge_arr.push(info.charge_money[i])
          }
          for (var i in recharge_arr) {
            charge_money.push(recharge_arr[i]);
          }
        }
        if (info.offset_momey == 1) {
          recharge_arr = info.charge_money[info.charge_money.length - 1];
          charge_money.push(recharge_arr);
        }
        that.setData({
          info: info,
          recharge_arr: recharge_arr
        })
      } else {
        util.showModal('提示', res.message, function() {
          wx.navigateBack({
            delta: 0
          })
        })
      }
    }, {
      card_id: card_id,

    })
  },
  iptMoney: function(e) {
    ipt_money = e.detail.value;
    if (ipt_money) { //限制只能输入一个点
      var ipt_money_arr = ipt_money.toString().split('.');
      if (ipt_money_arr.length > 2) {
        ipt_money = ipt_money_arr[0] + '.';
      }
    }
    //小数点后只能输2位
    var isNumeric = ipt_money;
    if (isNumeric) {
      var temp = isNumeric.toString().split('.');
      if (temp.length == 2) {
        if (temp[1].length >= 3) {
          isNumeric = temp[0] + '.' + temp[1].substring(0, 2);
        }
      }
    }
    ipt_money = isNumeric;
    this.setData({
      ipt_money: ipt_money
    })
  },
  fixedRecharge: function(e) {
    var charge_type = 0;
    var money = e.currentTarget.dataset.money;
    var fixed_charge_money = JSON.stringify(charge_money);
    var openid = util.getCache('openid');
    util.api('/api/card/charge', function(res) {
      if (res.error == 0) {
        var order_sn = res.content.order_sn;
        var goods_type = res.content.goods_type;
        wx.requestPayment({
          'timeStamp': res.content.timeStamp,
          'nonceStr': res.content.nonceStr,
          'package': res.content.package,
          'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
          'paySign': res.content.paySign,
          'success': function(res) {
            util.toast_success('支付成功');
            util.redirectTo({
              url: '/pages/usercardinfo/usercardinfo?card_no=' + card_no,
            })
          },
          'fail': function(res) {


          },
          'complete': function(res) {

          }

        })
      }
    }, {

      card_id: card_id,
      charge_type: charge_type,
      charge_money: fixed_charge_money,
      card_no: card_no,
      openid: openid,
      money: money
    })
  },
  customRecharge: function(e) {
    var charge_type = 1;
    if (ipt_money <= 0) {
      util.showModal('提示', '请输入正确的金额');
      return;
    }
    var openid = util.getCache('openid');
    var custom_charge_money = JSON.stringify(charge_money);
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api('/api/card/charge', function(res) {
      if (res.error == 0) {
        var order_sn = res.content.order_sn;
        var goods_type = res.content.goods_type;
        wx.requestPayment({
          'timeStamp': res.content.timeStamp,
          'nonceStr': res.content.nonceStr,
          'package': res.content.package,
          'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
          'paySign': res.content.paySign,
          'success': function(res) {
            util.toast_success('支付成功');
            util.redirectTo({
              url: '/pages/usercardinfo/usercardinfo?card_no=' + card_no,
            })
          },
          'fail': function(res) {
          },
          'complete': function(res) {
          }
        })
      }
    }, {

      card_id: card_id,
      charge_type: charge_type,
      charge_money: custom_charge_money,
      card_no: card_no,
      openid: openid,
      money: ipt_money,
      open_id: open_id,
      form_id: form_id
    })
  },
  toAgreement: function(e) {
    var that = this;
    util.navigateTo({
      url: '/pages/agreement/agreement?charge_money=' + JSON.stringify(that.data.info.charge_money)
    })
  },
})
