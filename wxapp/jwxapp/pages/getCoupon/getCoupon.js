/**
 * 优惠券详情
 * user：常乐
 */
let util = require("../../utils/util.js")
let config = require("../../utils/config.js")
var app = getApp();
var imageUrl = app.globalData.imageUrl
var couponSn;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: "http://miniimg.cn/",
    detailInfo:{},
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;
    couponSn = options.couponSn
    util.api("api/wxapp/coupon/detail",function(res){
      if(res.error == 0){
        _this.setData({
          detailInfo : res.content,
        })
      }
    },{couponSn:couponSn})
    console.log(this.data)
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