// pages/aftersaleorder/aftersaleorder.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    baseUrl: app.globalData.baseUrl,
    page: 1,
    last_page: 1,
    order_info: [],
    search: ''
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
      if (res.error == 0) {
        console.log(res)
        that.data.last_page = res.content.page.lastPage;
        that.setData({
          order_info: res.content.dataList
        })
      } else {
        util.showModal("提示", res.message, function () { });
        return false;
      }
    }, { search: that.data.search, currentPage: that.data.page })
  },
  // 商品详情
  to_item: function (e) {
    console.log(e)
    util.jumpLink('/pages/item/item?gid=' + e.currentTarget.dataset.goods_id);
  },
  // 售后详情
  to_detail: function (e) {
    console.log(e)
    util.jumpLink("/pages1/returndetail/returndetail?return_sn=" + e.currentTarget.dataset.order_sn);
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
    console.log('触底')
    var that = this;
    if (that.data.page == that.data.last_page) { return false };
    that.data.page = that.data.page + 1;
    wx.showLoading({
      title: '加载中···',
    })
    util.api('/api/wxapp/order/refund/list/search', function (res) {
      if (res.error == 0) {
        that.data.last_page = res.content.page.last_page;
        wx.hideLoading();
        that.setData({
          order_info: that.data.order_info.concat(res.content.dataList)
        })
      } else {
        util.showModal("提示", res.message, function () { });
        return false;
      }
    }, { search: that.data.search, page: that.data.page })
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
