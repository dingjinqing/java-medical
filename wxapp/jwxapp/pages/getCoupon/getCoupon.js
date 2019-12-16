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
    detailType: 1, // 详情类型(个人中心查看: 0, 装修查看: 1)
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;
    if (options.couponSn) {
      // 个人中心查看详情
      util.api("api/wxapp/coupon/detail",function(res){
        if(res.error == 0){
          _this.setData({
            detailInfo : res.content,
            detailType: 0
          })
        }
      },{couponSn:options.couponSn})
    } else {
      // 装修界面查看详情
      util.api("api/wxapp/coupon/detail",function(res){
        if(res.error == 0){
          _this.setData({
            detailInfo : res.content,
            detailType: 1
          })
        }
      },{couponSn:options.couponId})
    }
  }
})