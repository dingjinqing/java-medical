// pages/storelist/storelist.js
var util = require('../../utils/util');
var app = getApp();

global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    type: 0,
    goods_id: null,
    list: [],
    de_type: null,
    scan_stores: null,
    card_id: null,
    imageUrl: app.globalData.imageUrl,
    pageParams: {}
  },
  // 请求门店列表
  listRequest: function (that) {
    util.getUserLocation(function (location) {
      console.log('location:', location)
      util.api('/api/wxapp/store/list', function (res) {
        that.setData({
          list: res.content
        })
      }, {
        location: location,
        type: that.data.type, // 入口(type为0,普通入口;type为1,并且cardId不为空;表示入口为会员卡详情页;type为2 ,并且goodsId不为空表示入口为商品详情页自提/同城配送过来)
        scanStores: that.data.scan_stores, // 是否支持扫码购
        goodsId: that.data.goods_id, // 商品id
        cardId: that.data.card_id, // 会员卡id
        deliverType: that.data.de_type// 配送类型
      })
    })
  },
  // 点击门店跳转
  jumpUrl: function (e) {
    let id = e.currentTarget.dataset.id;
    let state = e.currentTarget.dataset.state;
    if (state === 1) {
      if (this.data.scan_stores) {
        util.navigateTo({
          url: '/pages/scancode/scancode?store_id=' + id
        })
      } else {
        util.navigateTo({
          url: '/pages/storeinfo/storeinfo?id=' + id
        })
      }
    } else {
      util.showModal('提示', '该门店未营业');
    }
  },
  // 点击随便逛逛
  onIndex: function (e) {
    util.navigateTo({ url: '/pages/index/index' })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    let de_type = options.de_type;// 自取还是同城配送
    let scan_stores = options.scan_stores; // 是否支持扫码购
    let goods_id = options.goods_id; // 商品id
    let card_id = options.cardId || null
    this.setData({
      scan_stores: scan_stores ? scan_stores : 0,
      de_type: de_type,
      goods_id: goods_id,
      card_id
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    let that = this;
    that.listRequest(that);
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading();
    var that = this;
    that.listRequest(that);
    wx.hideNavigationBarLoading();
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: 'pages/storelist/storelist?invite_id=' + util.getCache('user_id')
    }
  }
})