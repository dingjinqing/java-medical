var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var ins_info = [];
var edit_money = 0;
var edit_msg = 0;
var edit_name = 0;
var payed_money;
var payed_msg;
var payed_name;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    ins_info:[],
    payed_money:0,
    payed_msg:'',
    payed_name:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    mobile = util.getCache('mobile');
    this.data.order_sn = options.order_sn;
    var that = this;
    util.api('/api/wxapp/insteadpay/waitpay', function (res) {
        if(res.error == 0){
          console.log(res.content);
          ins_info = res.content;
          payed_money = ins_info.doc_money.money_paid;
          payed_msg = ins_info.message;
          payed_name = ins_info.user.username;
          that.data.is_can_edit = ins_info.doc_money.is_can_edit;
          that.setData({
            is_can_edit: that.data.is_can_edit,
            ins_info: ins_info,
            payed_money: payed_money,
            payed_name: payed_name,
            payed_msg:payed_msg
          })
        }else{
          util.showModal("提示",res.message,function(){
            if (res.error == 10) {
                util.redirectTo({
                  url: '../../' + 'pages/insteadinfo/insteadinfo' + '?order_sn=' + that.data.order_sn,
              })
            }
          },false);
          return false;
        }
    }, { order_sn: this.data.order_sn})
  },
  changemoney:function(e){
    if (!this.data.is_can_edit) return false;
    if (e.detail.value == ""){
      payed_money = '';
      util.showModal('提示', "请输入付款金额");
      return false;
    }else{
      payed_money = parseFloat(e.detail.value);
    }
    for (var i = 0; i < ins_info.doc_money.doc_price.length; i++) {
      if (payed_money == parseFloat(ins_info.doc_money.doc_price[i].price)){
        ins_info.doc_money.doc_price[i].checke = true;
      }
    }
    this.setData({
      ins_info: ins_info
    })
  },
  change_radio:function(e){
    for (var i = 0; i < ins_info.doc_money.doc_price.length; i++){
      ins_info.doc_money.doc_price[i].checke = false;
    }
    this.setData({
      ins_info: ins_info
    })
  },
  changemsg:function(e){
    payed_msg = e.detail.value;
  },
  changename:function(e){
    payed_name = e.detail.value;
  },
  radioChange:function(e){
    if (ins_info.doc_money.doc_price != ""){
      for (var i in ins_info.doc_money.doc_price){
        if(i == e.detail.value){
          payed_money = ins_info.doc_money.doc_price[i].price;
          this.setData({
            payed_money: payed_money
          })
        }
      }
    }
  },
  checkboxChange:function(e){
    if (e.detail.value == "hide_name"){
      payed_name = "当代活雷锋"
    }
  },
  btn_to_payed:function(e){
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    if (payed_money == ""){
      util.showModal('提示',"请输入付款金额");
      return false
    }
    util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id })
    util.api('/api/wxapp/insteadpay/topay', function (res) {
      if (res.error == 0) {
        console.log(res.content);
        wx.requestPayment({
          'timeStamp': res.content.timeStamp,
          'nonceStr': res.content.nonceStr,
          'package': res.content.package,
          'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
          'paySign': res.content.paySign,
          'success': function (res) {
            util.toast_success('支付成功');
            util.redirectTo({
              url: '/pages/insteadinfo/insteadinfo?order_sn=' + that.data.order_sn,
            })
          },
          'fail': function (res) {

          },
          'complete': function (res) {
          }
        });
      } else {
        util.showModal("提示", res.message, function () {
          if (res.error == 10) {
            util.redirectTo({
              url: '../../pages/insteadinfo/insteadinfo?order_sn=' + that.data.order_sn,
            })
          }
        }, false);
        return false;
      }
    }, { order_sn: this.data.order_sn, money_paid: payed_money, username: payed_name, message: payed_msg})
  },

})
