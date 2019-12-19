/**
 * 优惠券列表
 * user:常乐
 */
let util = require("../../utils/util.js")
let config = require("../../utils/config.js")
var app = getApp();
var set_time_out;
// pages/test/test.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    page_id:1,
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
    this_type: 0,
    allCoupon:[],
    page: 1,
  },
 
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;
    _this.dataList()
  },

  /**
   * 优惠券列表
   */
  dataList: function (){
    var _this = this;
    wx.showLoading({
      title: '加载中···',
    })
    clearTimeout(set_time_out);
    util.api('/api/wxapp/coupon/list', function (res) {
      if (res.error == 0) {
        _this.setData({
          unusedNum: res.content.unusedNum,
          usedNum: res.content.usedNum,
          expiredNum: res.content.expiredNum,
          allCoupon: res.content.couponList.dataList
        }) 
        // 格式化时间
        if (_this.data.allCoupon.length > 0) {
          _this.data.allCoupon.forEach(function (item) {
            if (item.startTime && item.endTime) {
              item.startTime = item.startTime.toString().slice(0, 10)
              item.endTime = item.endTime.toString().slice(0, 10)
            }
            item.remain_seconds_all = item.remainHours * 3600 + item.remainMinutes * 60 + item.remainSeconds
          })
          // 倒计时
          _this.countdown(_this, _this.data.allCoupon);
        }

        _this.setData({
          allCoupon: _this.data.allCoupon
        }) 
        wx.hideLoading();
      } else {
        util.showModal("提示", res.message, function () {
          util.jumpLink("pages/index/index", 'redirectTo');
        }, false);
        return false;
      }
    }, { nav: _this.data.this_type });
  },

  /**
   * 倒计时
   */
  countdown: function (that, dataList) {
    set_time_out = setTimeout(function () {
      // 放在最后--
      for (var i in dataList) {
        dataList[i].remain_seconds_all -= 1;
        if (dataList[i].remain_seconds_all < 0 || dataList[i].remain_seconds_all == NaN) {
          dataList[i].time_tips = "";
        } else {
          dataList[i].time_tips = util.dateformat(dataList[i].remain_seconds_all);
          console.log(dataList[i].time_tips)
        }
      }
      that.setData({
        allCoupon: dataList
      });
      that.countdown(that, dataList);
    }, 1000)
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
        this_type: 0,
      }) 
    }
    if (name == 'used') {
      _this.setData({
        this_type:1,
      }) 
    }
    if (name == 'time') {
      _this.setData({
        this_type: 2,
      })
    }
    _this.dataList()
    // _this.data.page = 1;
  },

  /**
   * 优惠券详情
   */
  couponDetail:function(opt){
    var couponSn = opt.currentTarget.dataset.couponsn;
    util.jumpLink('/pages/getCoupon/getCoupon?couponSn=' + couponSn);
  },

  /**
   * 券购搜素
   */
  to_search: function (opt) {
    var coupon_sn = opt.currentTarget.dataset.coupon_sn;
    util.jumpLink('/pages/searchs/search?coupon_sn=' + coupon_sn);
  }
})