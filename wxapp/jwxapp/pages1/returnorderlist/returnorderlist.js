// pages1/returnorderlist/returnorderlist.js
var util = require('../../utils/util.js');
var app = getApp();
var imageUrl = app.globalData.imageUrl;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    click_look: imageUrl + 'image/wxapp/click_look.png',
    orderSn: '',
    orderId: '',
    retId: '',
    returnFlag: 0, // 是否展示创建售后申请按钮
    returnOrderList: [], // 售后单列表
    createTime: '', // 下单时间

    return: ['仅退款', '退货退款', '仅退运费', '手动退款', '换货'], // 售后类型
    reasone: ['协商一致退款', '未按约定时间发货', '缺货', '拍错/多拍/不想要', '其他'], // 退货退款原因
    reasone_huan: ['协商一致换货', '商品与页面描述不符', '发错货', '商品损坏', '其他'], // 换货原因
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let orderSn = options.order_sn
    let orderId = options.order_id
    let retId = options.ret_id
    this.setData({
      orderSn: orderSn,
      orderId: orderId,
      retId: retId
    })
    this.initData()
  },

  initData () {
    let that = this
    util.api('/api/wxapp/order/refund/list', function (res) {
      if (res.error === 0) {
        let content = res.content
        that.setData({
          returnFlag: content.returnFlag,
          returnOrderlist: content.returnOrderList,
          createTime: content.createTime
        })
      }
    }, {
      orderSn: that.data.orderSn,
      orderId: that.data.orderId,
      retId: that.data.retId
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})