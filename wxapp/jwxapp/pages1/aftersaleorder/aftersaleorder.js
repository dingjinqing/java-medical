// pages/aftersaleorder/aftersaleorder.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var order_info = [];

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    baseUrl: app.globalData.baseUrl,
    page: 1,
    last_page: 1,
    order_info: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    this.order_request(that);
  },
  // 列表数据请求
  order_request (that) {
    console.log(that)
    util.api('/api/wxapp/order/refund/list/search', function (res) {
      //   if (res.error == 0) {
      //     var order_info = [];
      //     that.data.last_page = res.content.list.last_page;
      //     if (res.content.list.data != "") {
      //       order_info = res.content.list.data;
      //     } else {
      //       order_info = ""
      //     }
      //     that.setData({
      //       order_info: order_info
      //     })
      //   } else {
      //     util.showModal("提示", res.message, function () { });
      //     return false;
      //   }
    }, { search: that.data.search, page: that.data.page })
  },
  // 商品详情
  to_item: function (e) {
    util.jumpLink('/pages/item/item?goods_id=' + e.currentTarget.dataset.goods_id);
  },
  // 售后详情
  to_detail: function (e) {
    util.jumpLink("/pages3/returnorder/returnorder?order_sn=" + e.currentTarget.dataset.order_sn + "&ret_id=" + e.currentTarget.dataset.ret_id + "&submit_shipping=0");
  },
  // 搜索
  bindSearch: function (e) {
    var that = this;
    that.data.page = 1;
    this.order_request(that);
  },
  // 重置搜索
  clear_value: function () {
    this.data.search = '';
    this.setData({
      input_value: ""
    })
  },
  save: function (e) {
    var ipt_val = e.detail.value;
    this.data.search = ipt_val;
    this.setData({
      input_value: ipt_val
    })
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
    var that = this;
    if (that.data.page == that.data.last_page) { return false };
    that.data.page = that.data.page + 1;
    wx.showLoading({
      title: '加载中···',
    })
    // util.api('/api/wxapp/return/aftersalelist', function (res) {
    //   if (res.error == 0) {
    //     var order_info = [];
    //     that.data.last_page = res.content.list.last_page;
    //     if (res.content.list.data != "") {
    //       order_info = res.content.list.data;
    //     } else {
    //       order_info = ""
    //     }
    //     wx.hideLoading();
    //     that.setData({
    //       order_info: that.data.order_info.concat(order_info)
    //     })
    //   } else {
    //     util.showModal("提示", res.message, function () { });
    //     return false;
    //   }
    // }, { search: that.data.search, page: that.data.page })
  },

  /**
    * 用户点击右上角分享
    */
  onShareAppMessage: function () {
    return {
      path: this.route + '?invite_id=' + util.getCache('user_id')
    }
  },
})
