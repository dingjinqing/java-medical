var util = require('../../utils/util.js')
var barcode = require('../../utils/barcode.js');
var qrcode = require('../../utils/qrcode01.js');
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var store_id;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl:app.globalData.imageUrl,
    no_code:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    if (!util.check_setting(options)) return;
    store_id = options.store_id;

    util.api('/api/wxapp/store/scan/code',function(res){
      if(res.error == 0){
        if (res.content.order_list != '') {
          for (var i in res.content.order_list) {
            barcode.barcode(res.content.order_list[i].verify_code, res.content.order_list[i].verify_code, 500, 120);
            qrcode.qrcode('qr' + res.content.order_list[i].verify_code,res.content.order_list[i].verify_code, 500, 500);
            res.content.order_list[i].total_money = (parseFloat(res.content.order_list[i].score_discount)
              + parseFloat(res.content.order_list[i].use_account) 
              + parseFloat(res.content.order_list[i].money_paid) 
              + parseFloat(res.content.order_list[i].member_card_balance)).toFixed(2);
          }
          that.setData({
            no_code: false,
            order_list: res.content.order_list
          })
        }
      }
    }, {   store_id: store_id})
  },
 


})