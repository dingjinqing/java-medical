// pages1/presentlist/presentlist.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var get_gift_list = [];
var send_gift_list = [];
var if_send = 1;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page:1,
    last_page:1,
    get_gift_list:[],
    page1:1,
    last_page1:1,
    send_gift_list:[],
    if_send:1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    if(options.is_get && options.is_get == 1){
      if_send = 0;
      that.setData({
        if_send:0
      })
    }
    list_request(that)
  },
  change_nav:function(e){
    let if_sends = e.currentTarget.dataset.if_send;
    let that = this;
    if_send = if_sends;
    that.setData({
      if_send: if_sends
    })
    list_request(that)
  },
  to_own_order:function(e){
    let order_sns = e.currentTarget.dataset.order_sn;
    util.jumpLink("pages1/presentdetail/presentdetail?order_sn=" + order_sns)
  },
  to_get_order:function(e){
    let order_sns = e.currentTarget.dataset.orders;
    let order_sta = e.currentTarget.dataset.sta;
    if (order_sta == 0){
      util.jumpLink("pages1/presentcheckout/presentcheckout?order_sn=" + order_sns );
    }else{
      util.jumpLink("pages1/presentcheckout/presentcheckout?order_sn=" + order_sns + "&is_from_detail=1");
    }
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
    if(if_send == 1){
      var that = this;
      // that.setData({
      //   is_load: 1
      // })
      if (that.data.page1 == that.data.last_page1) {
        // that.setData({
        //   is_load: 0
        // })
        return;
      }
      that.data.page1 = that.data.page1 + 1;
      util.api('/api/wxapp/givegift/order/mainlist', function (res) {
        if (res.error == 0) {
          var send_giftL = res.content;
          var send_gift_list = [];
          if (send_giftL.data.length > 0) {
            send_gift_list = send_giftL.data;
          }
          that.setData({
            send_gift_list: that.data.send_gift_list.concat(send_gift_list)
          })
        } else {
          util.showModal('提示', res.message, function () {
            util.jumpLink("/pages/index/index", 'redirectTo')
          }, false);
          return false;
        }
      }, { page: that.data.page1 })
    }else{
      var that = this;
      // that.setData({
      //   is_load: 1
      // })
      if (that.data.page == that.data.last_page) {
        // that.setData({
        //   is_load: 0
        // })
        return;
      }
      that.data.page = that.data.page + 1;
      util.api('/api/wxapp/givegift/order/sublist', function (res) {
        if (res.error == 0) {
          var get_giftL = res.content;
          var get_gift_list = [];
          if (get_giftL.data.length > 0) {
            get_gift_list = get_giftL.data;
          }
          for (var i = 0; i < get_gift_list.length; i++) {
            if (get_gift_list[i].order_status == 3) {
              get_gift_list[i].order_status_name = "待发货"
            }
          }
          that.setData({
            get_gift_list: that.data.get_gift_list.concat(get_gift_list)
          })
        } else {
          util.showModal('提示', res.message, function () {
            util.jumpLink("/pages/index/index", 'redirectTo')
          }, false);
          return false;
        }
      }, { page: that.data.page})
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
function list_request(that){
  if(if_send == 1){
    util.api('/api/wxapp/givegift/order/mainlist',function(res){
      if(res.error == 0){
        var send_giftL = res.content;
        var send_gift_list = [];
        that.data.last_page1 = send_giftL.last_page;
        if (send_giftL.data.length > 0) {
          send_gift_list = send_giftL.data;
        }
        that.setData({
          send_gift_list: send_gift_list
        })
      }else{
        util.showModal('提示', res.message, function () {
          util.jumpLink("/pages/index/index", 'redirectTo')
        }, false);
        return false;
      }
    },{page:that.data.page1})
  }else{
    util.api('/api/wxapp/givegift/order/sublist',function(res){
        if(res.error == 0){
          var get_giftL = res.content;
          var get_gift_list = [];
          that.data.last_page = get_giftL.last_page;
          if(get_giftL.data.length > 0){
            get_gift_list = get_giftL.data;
          }
          for (var i = 0; i < get_gift_list.length;i++){
            if (get_gift_list[i].order_status == 3){
              get_gift_list[i].order_status_name = "待发货"
            }
          }
          that.setData({
            get_gift_list:get_gift_list
          })
        }else{
          util.showModal('提示', res.message, function () {
            util.jumpLink("/pages/index/index", 'redirectTo')
          }, false);
          return false;
        }
    },{page:that.data.page})
  }
}