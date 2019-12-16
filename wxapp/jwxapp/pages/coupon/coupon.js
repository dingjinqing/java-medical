/**
 * 优惠券列表
 * user:常乐
 */
let util = require("../../utils/util.js")
let config = require("../../utils/config.js")
var app = getApp();
// pages/test/test.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    page_id:0,
    can_used_flag: true,
    cou_used_flag: false,
    cou_guoqi_flag: false,
    can_used_header_flag: false,
    cou_used_header_flag: false,
    cou_guoqi_header_flag: false,
    // imageUrl: app.globalData.imageUrl,
    imageUrl: "http://miniimg.cn",
    unusedNum:0,
    usedNum: 0,
    expiredNum:0,
    this_type: 1,
    cou_list: {},
    allCoupon:{},
    page: 1,
  },
 
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;
    var page_id = 1;
    _this.dataList(page_id);
    console.log(_this.allCoupon)
  },

  /**
   * 优惠券列表
   */
  dataList: function (page_id=0,nav){
    var _this = this;
    wx.showLoading({
      title: '加载中···',
    })
    util.api('/api/wxapp/coupon/list', function (res) {
      console.log(res)
      if (res.error == 0) {
        _this.setData({
          unusedNum: res.content.unusedNum,
          usedNum: res.content.usedNum,
          expiredNum: res.content.expiredNum,
          allCoupon: res.content.couponList.dataList
        }) 
        // 格式化时间
        console.log(_this.data.allCoupon)
        if (_this.data.allCoupon) {
          _this.data.allCoupon.forEach(function (item) {
            if (item.startTime && item.endTime) {
              item.startTime = item.startTime.toString().slice(0, 10)
              item.endTime = item.endTime.toString().slice(0, 10)
            }
          })
        }
        _this.setData({
          allCoupon: _this.data.allCoupon
        }) 
        wx.hideLoading();
        _this.setData({
          cou_list: res.content.unused,
        })
        
      } else {
        util.showModal("提示", res.message, function () {
          util.jumpLink("pages/index/index", 'redirectTo');
        }, false);
        return false;
      }
    }, {page: page_id,nav:nav});
  },

  /**
   * 优惠券状态tab切换
   */
  change: function (e) {
   
    var _this = this;
    var name = e.target.dataset.name;
    console.log(name)
    if (name == 'can') {
      _this.setData({
        this_type: 1,
        cou_list: this.data.allCoupon.unused
      }) 
      this.dataList(0,0)
    }
    if (name == 'used') {
      _this.setData({
        this_type:2,
        cou_list: this.data.allCoupon.used
      }) 
      this.dataList(0,1)
    }
    if (name == 'time') {
      _this.setData({
        this_type: 3,
        cou_list: this.data.allCoupon.expired
      })
      this.dataList(0,2)
    }
    _this.data.page = 1;
  },

  /**
   * 优惠券详情
   */
  couponDetail:function(opt){
    var couponSn = opt.currentTarget.dataset.couponsn;
    util.jumpLink('/pages/getCoupon/getCoupon?couponSn=' + couponSn);
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